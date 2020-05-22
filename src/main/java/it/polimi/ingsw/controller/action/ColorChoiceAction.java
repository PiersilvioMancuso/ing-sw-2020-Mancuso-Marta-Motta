package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.controllerState.ColorChoiceControllerState;
import it.polimi.ingsw.controller.controllerState.WorkerSetupControllerState;
import it.polimi.ingsw.model.ModelColor;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.messages.controllersMessages.Ack;
import it.polimi.ingsw.messages.controllersMessages.Nack;
import it.polimi.ingsw.messages.modelViewMessages.ModelColorListUpdate;
import it.polimi.ingsw.messages.modelViewMessages.ModelUpdate;
import it.polimi.ingsw.view.Command;

import java.util.List;

/**Color Choice Action Class
 * @author Piersilvio Mancuso
 */
public class ColorChoiceAction extends Action{

    private final int colorChosen;

    // -------------- CONSTRUCTOR ----------------

    /**Color Choice Action Constructor
     * @param data is a String with the following pattern: 'username=%userName,color=%color'
     */
    public ColorChoiceAction(String data){
        super();
        this.className = "ColorChoiceAction";
        String[] message = data.split(";");
        this.username = message[0].split("=")[1];
        this.colorChosen = Integer.parseInt(message[1].split("=")[1]);
    }

    // ------------------ GETTER -------------------

    /**Color Choice Getter
     * @return an Integer who represents the color Chosen
     */
    public int getColorChosen() {
        return colorChosen;
    }


    // ----------------- EFFECT -------------------

    /**Set User's color
     * @param user is the user to which the color will be set
     * @param colorList is the list of remaining colors
     */
    public void executeAction(User user, List<ModelColor> colorList){
        user.setColor(colorList.get(colorChosen));
        colorList.remove(colorChosen);
    }


    // ----------------- CONTROLLER ACTION -----------------

    /**Set the color chosen to the user
     * @param remoteController is the remote controller to which run the action
     */
    public void controlAction(RemoteController remoteController){

        //Get the Action's user
        User user = remoteController.getUserFromUsername(username);

        // If color chosen is not into the list of colors, it will send a Nack
        if (colorChosen <0 || colorChosen >= remoteController.getModelColorList().size()){
            String errorMessage = "Invalid Color Chosen";
            remoteController.setResponse( new Nack(errorMessage, user.getUsername(), Command.COLOR));
        }
        else {
            executeAction(user, remoteController.getModelColorList());
            remoteController.getModelGame().nextUser();
            user = remoteController.getModelGame().getCurrentUser();
            remoteController.getModelGame().addUpdate(new ModelUpdate(remoteController.getModelGame()));

            if (user.getColor()!= null){
                remoteController.getModelGame().startGame();
                user = remoteController.getModelGame().getCurrentUser();

                remoteController.setResponse(new Ack(user.getUsername(), Command.SET_WORKER_POSITION, new WorkerSetupControllerState()));
            }
            else {
                remoteController.getModelGame().addUpdate(new ModelColorListUpdate(remoteController.getModelColorList()));
                remoteController.setResponse(new Ack(user.getUsername(), Command.COLOR, new ColorChoiceControllerState()));
            }
        }
    }
}
