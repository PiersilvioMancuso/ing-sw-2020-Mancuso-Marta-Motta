package it.polimi.ingsw.controller.controllerState;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.GodChoiceAction;

/**God Choice Controller State Class
 * @author Piersilvio Mancuso
 */
public class GodChoiceControllerState extends ControllerState{


    // ------------- CONSTRUCTOR ------------

    /**GodChoiceControllerState Constructor
     */
    public GodChoiceControllerState() {
        this.className = getClass().getSimpleName();
    }

    /**Create a God Choice Action
     * @param string is the string with which an Action will be created
     * @return a God Choice Action object
     */
    @Override
    public Action createAction(String string) {
        return new GodChoiceAction(string);
    }
}
