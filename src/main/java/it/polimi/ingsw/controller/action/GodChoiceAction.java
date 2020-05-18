package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.controllerState.ColorChoiceControllerState;
import it.polimi.ingsw.controller.controllerState.GodChoiceControllerState;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.controllersMessages.Ack;
import it.polimi.ingsw.messages.controllersMessages.Nack;
import it.polimi.ingsw.messages.modelViewMessages.GodListUpdate;
import it.polimi.ingsw.messages.modelViewMessages.ModelColorListUpdate;
import it.polimi.ingsw.messages.modelViewMessages.ModelUpdate;
import it.polimi.ingsw.view.Command;

import java.util.List;

/**God Choice Action
 * @author Piersilvio Mancuso
 */
public class GodChoiceAction extends Action{
    private int godChosen;


    // --------------- CONSTRUCTOR ------------------

    /**Create The GodChoiceAction Object
     * @param data is a string with the following pattern: 'username=%s;god=%s'
     */
    public GodChoiceAction(String data){
        super();
        this.className = "GodChoiceAction";
        String[] message = data.split(";");
        this.username = message[0].split("=")[1];
        this.godChosen = Integer.parseInt(message[1].split("=")[1]);
    }


    // --------------- GETTER -----------------

    /**God Getter
     * @return an Integer that represents the god chosen
     */
    public int getGodChosen() {
        return godChosen;
    }



    // ------------------ ACTION ----------------------

    /**Execute the GodChoiceAction: Set the user's God
     * @param godEnum is a list of Gods
     * @param userList is the list of all players
     */
    public void executeAction(List<GodEnum> godEnum, List<User> userList){

        for (User user: userList){
            if (user.getUsername().equals(username)) {
                user.setGod(godEnum.get(godChosen).getGod());
            }
        }
        godEnum.remove(godChosen);
    }


    // ----------------- CONTROLLER ACTION -----------------

    /**Set the God to the user from a List of Gods
     * @param remoteController is the remote controller to which run the action
     */
    public void controlAction(RemoteController remoteController){

        // If the god chosen id is not in the GodEnumList of Remote Controller, it will send a Nack telling him that is an Incorrect Choice
        if (godChosen >= remoteController.getGodEnumList().size() || getGodChosen() < 0){
            String errorMessage = "Incorrect God Chosen";
            remoteController.setResponse(new Nack(errorMessage, username, Command.GOD));
        }
        else {
            // Set user's God, send Updates to all Clients
            executeAction(remoteController.getGodEnumList(),remoteController.getPlayerList());
            remoteController.getModelGame().setUserList(remoteController.getPlayerList());
            remoteController.getModelGame().nextUser();
            remoteController.getModelGame().addUpdate(new ModelUpdate(remoteController.getModelGame()));

            User user = remoteController.getModelGame().getCurrentUser();

            //If all users have already their god, the next user will receive a ColorChoice Controller State, with the list of all colors
            if (user.getGod() != null){
                remoteController.getModelGame().addUpdate(new ModelColorListUpdate(remoteController.getModelColorList()));
                remoteController.setResponse(new Ack(user.getUsername(), Command.COLOR, new ColorChoiceControllerState()));
            }
            else {
                //If not all Users have already their god, the next user will receive a list of all Available Gods
                remoteController.getModelGame().addUpdate(new GodListUpdate(remoteController.getGodEnumList()));
                remoteController.setResponse(new Ack(user.getUsername(), Command.GOD, new GodChoiceControllerState()));
            }

        }
    }
}

