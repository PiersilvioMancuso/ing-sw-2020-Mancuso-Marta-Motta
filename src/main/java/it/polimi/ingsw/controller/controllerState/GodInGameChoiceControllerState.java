package it.polimi.ingsw.controller.controllerState;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.GodInGameChoiceAction;

/**GodInGameChoiceControllerState Class
 * @author Piersilvio Mancuso
 */
public class GodInGameChoiceControllerState extends ControllerState{

    // -------------- CONSTRUCTOR ------------

    /**GodInGameChoiceController Constructor
     */
    public GodInGameChoiceControllerState() {
        this.className = "GodInGameChoiceControllerState";
    }


    // --------- ACTION CREATION ----------------

    /**Create a God In Game Choice Action
     * @param string is the string with which an Action will be created
     * @return a God in Game Choice Action
     */
    @Override
    public Action createAction(String string) {
        return new GodInGameChoiceAction(string);
    }
}
