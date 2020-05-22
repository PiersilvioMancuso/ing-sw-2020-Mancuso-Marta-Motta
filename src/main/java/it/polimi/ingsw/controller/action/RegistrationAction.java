package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.controllerState.ActivatePowerControllerState;
import it.polimi.ingsw.controller.controllerState.GodInGameChoiceControllerState;
import it.polimi.ingsw.controller.controllerState.PlayersInGameChoiceControllerState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.messages.controllersMessages.Ack;
import it.polimi.ingsw.messages.controllersMessages.Nack;
import it.polimi.ingsw.messages.controllersMessages.RegistrationAck;
import it.polimi.ingsw.messages.modelViewMessages.GodListUpdate;
import it.polimi.ingsw.messages.modelViewMessages.ModelUpdate;
import it.polimi.ingsw.view.Command;

import java.util.List;

/**RegistrationAction Class
 * @author Piersilvio Mancuso
 */
public class RegistrationAction extends Action{
    int age;
    private final String ipAddress;

    /**Register Action Constructor
     * @param data is the String that will be analyzed to set Register Action's fields
     */
    public RegistrationAction(String data){
        super();
        this.className = "RegistrationAction";
        String [] messageComponent = data.split(";");
        this.username = messageComponent[0].split("=")[1];
        this.age = Integer.parseInt(messageComponent[2].split("=")[1]);
        this.ipAddress = messageComponent[1].split("=")[1];
    }


    // -------------------- GETTER --------------------------

    /**User's Age Getter
     * @return the age of the player
     */
    public int getAge() {
        return age;
    }

    /**Server's Ip Address Getter
     * @return the IpAddress of the server to which to connect
     */
    public String getIpAddress() {
        return ipAddress;
    }


    // -------------------- ACTION -------------------------

    /**Register the Player into the Game Lobby
     */
    public void executeAction(List<User> userList){
        User user = new User(username);
        user.setAge(age);
        userList.add(user);
    }


    // ----------------- CONTROLLER ACTION -----------------


    /**Execute the action and set remoteController's response to send
     * @param remoteController is the remoteController that will run the actions
     */
    public void controlAction(RemoteController remoteController){


        // ---------- Lobby Full
        if (remoteController.getPlayerList().size() + 1 > remoteController.getMaxPlayers()){
            String message = "Lobby is full, please try again in some minutes";
            remoteController.setResponse(new Nack(message, username, Command.QUIT));
        }

        // --------- Username Already in use from another user
        else if (remoteController.checkUserExistenceWithUsername(username) || username.equals(RemoteController.getENDNAME())){
            String message = "Username already used by another player, please set another username";
            remoteController.setResponse(new Nack(message, username, Command.REGISTER));
        }

        // --------- Registration can be executed
        else {

            executeAction(remoteController.getPlayerList());

            //Registration Ack Creation
            remoteController.setResponse(new RegistrationAck(username, Command.PLAYERS));



            // If user is the first user, it will receive another Ack asking him to set number of players in the game
            if (remoteController.getPlayerList().size() == 1){

                //Send the Registration Ack
                remoteController.sendResponse();

                //Create a PlayersInGameChoice Ack
                remoteController.setResponse(new Ack(username, Command.PLAYERS, new PlayersInGameChoiceControllerState()));
            }

            // If with user's registration the lobby is full then the modelGame can be created and it will start the Setup state
            else if (remoteController.getPlayerList().size() == remoteController.getMaxPlayers() && remoteController.getMaxPlayers() > 1){

                // Send the Registration Ack
                remoteController.sendResponse();

                //Set that the game is started
                remoteController.setGameStarted(true);

                if (remoteController.checkUsersInCopy()){
                    remoteController.setModelGame(remoteController.getModelCopy());
                    remoteController.setPlayerList(remoteController.getModelCopy().getUserList());
                    remoteController.getModelGame().setServer(remoteController.getServer());
                    remoteController.setResponse(new Ack(remoteController.getModelGame().getCurrentUser().getUsername(), Command.USE_GOD_POWER, new ActivatePowerControllerState()));
                    remoteController.getModelGame().addUpdate(new ModelUpdate(remoteController.getModelGame()));
                    remoteController.setGameStarted(true);
                    return;
                }


                // Initialize the ModelGame with its default properties
                remoteController.setModelGame(new ModelGame());
                remoteController.getModelGame().setServer(remoteController.getServer());
                remoteController.getModelGame().setUserList(remoteController.getPlayerList());

                remoteController.getModelGame().addUpdate(new ModelUpdate(remoteController.getModelGame()));
                remoteController.getModelGame().addUpdate(new GodListUpdate(remoteController.getGodEnumList()));


                //Set the Ack to send to the youngest user
                User user = remoteController.getYoungestUser();
                remoteController.getModelGame().setCurrentUser(remoteController.getPlayerList().indexOf(user));
                if (remoteController.getPlayerList().size() == 2){
                    remoteController.setResponse( new Ack(user.getUsername(), Command.GOD_LIST_TWO, new GodInGameChoiceControllerState()));
                }

                else  {
                    remoteController.setResponse( new Ack(user.getUsername(), Command.GOD_LIST_THREE, new GodInGameChoiceControllerState()));
                }

            }
        }

    }

}
