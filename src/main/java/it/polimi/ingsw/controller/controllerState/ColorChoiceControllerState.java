package it.polimi.ingsw.controller.controllerState;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.ColorChoiceAction;

/**Color Choice Controller State Class
 * @author Piersilvio Mancuso
 */
public class ColorChoiceControllerState extends ControllerState{

    // ------------ CONSTRUCTOR ------------

    /**ColorChoiceControllerState Constructor
     */
    public ColorChoiceControllerState() {
        this.className = getClass().getSimpleName();
    }


    // ------------ EFFECT ----------------

    /**Create a Color Choice Action
     * @param string is the string with which an Action will be created
     * @return a Color Choice Action object
     */
    @Override
    public Action createAction(String string) {
        return new ColorChoiceAction(string);
    }
}
