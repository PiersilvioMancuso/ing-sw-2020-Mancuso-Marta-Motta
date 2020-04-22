package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.event.*;
import it.polimi.ingsw.model.event.response.*;

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
     * @param modelGame is the model of the game
     * @param godList is a list of Gods
     * @return an Ack if the action has been executed, otherwise a Nack
     */
    public void executeAction(ModelGame modelGame, GodList godList){
        if ((godChosen >= godList.getGodList().size() || godChosen < 0)) throw new IllegalArgumentException("Color has to be in the list");
        else {
            for (User user: modelGame.getUserList()){
                if (user.getUsername().equals(getUsername())) {
                    user.setGod(godList.getGodList().get(godChosen));

                }
            }
        }
    }
}

