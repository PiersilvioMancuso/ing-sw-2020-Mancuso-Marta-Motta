package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.controllerState.GodInGameChoiceControllerState;
import it.polimi.ingsw.controller.controllerState.PlayersInGameChoiceControllerState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.messages.controllersMessages.Ack;
import it.polimi.ingsw.model.messages.controllersMessages.Nack;
import it.polimi.ingsw.model.messages.controllersMessages.RegistrationAck;
import it.polimi.ingsw.model.messages.modelViewMessages.GodListUpdate;
import it.polimi.ingsw.view.Command;

import java.util.List;


/**RegistrationAction Class
 * @author Piersilvio Mancuso
 */
public class RegistrationAction extends Action{


    String userName;
    int age;
    String ipAddress;

    /**Register Action Constructor
     * @param data is the String that will be analyzed to set Register Action's fields
     */
    public RegistrationAction(String data){
        super();
        this.className = getClass().getSimpleName();
        String [] messageComponent = data.split(";");
        this.userName = messageComponent[0].substring(9);
        this.age = Integer.parseInt(messageComponent[1].substring(4));
        this.ipAddress = messageComponent[2].substring(10);
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

    /**Username Getter
     * @return the username of the player
     */
    public String getUserName() {
        return userName;
    }


    // -------------------- ACTION -------------------------

    /**Register the Player into the Game Lobby
     * @return the Response of the model's modifies
     */
    public void executeAction(List<User> userList){
        User user = new User(userName);
        user.setAge(age);
        userList.add(user);
    }


    /**Execute the action and set remoteController's response to send
     * @param remoteController is the remoteController that will run the actions
     */
    public void controlAction(RemoteController remoteController){

        // ---------- Lobby Full
        if (remoteController.getPlayerList().size() + 1 > remoteController.getMaxPlayers()){
            String message = "Lobby is full, please try again in some minutes";
            remoteController.setResponse(new Nack(message, userName, Command.QUIT));
        }

        // --------- Username Already in use from another user
        else if (remoteController.checkUserExistenceWithUsername(userName)){
            String message = "Username already used by another player, please set another username";
            remoteController.setResponse(new Nack(message, userName, Command.REGISTER));
        }

        // --------- Registration can be executed
        else {
            remoteController.getPlayerList().add(new User(userName));
            remoteController.setResponse(new RegistrationAck(userName, null));


            remoteController.getUserFromUsername(userName).setAge(age);

            // --------------
            if (remoteController.getPlayerList().size() == 1){

                remoteController.sendResponse();
                remoteController.setResponse(new Ack(userName, Command.PLAYERS, new PlayersInGameChoiceControllerState()));

            }

            else if (remoteController.getPlayerList().size() == remoteController.getMaxPlayers() && remoteController.getPlayerList().size() > 1){
                String username = remoteController.getYoungerUsername();
                remoteController.sendResponse();
                remoteController.setGameStarted(true);

                //TODO: A.F. Persistence
                remoteController.setModelGame(new ModelGame());
                remoteController.setServer(remoteController.getServer());
                remoteController.getModelGame().setUserList(remoteController.getPlayerList());
                remoteController.getModelGame().addUpdate(new GodListUpdate(remoteController.getGodEnumList()));

                if (remoteController.getPlayerList().size() == 2){
                    remoteController.setResponse( new Ack(username, Command.GOD_LIST_TWO, new GodInGameChoiceControllerState()));
                }

                else  {
                    remoteController.setResponse( new Ack(username, Command.GOD_LIST_THREE, new GodInGameChoiceControllerState()));
                }

            }
        }

    }

}
