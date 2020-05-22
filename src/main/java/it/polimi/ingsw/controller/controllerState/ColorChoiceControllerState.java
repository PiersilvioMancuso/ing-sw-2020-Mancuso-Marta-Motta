package it.polimi.ingsw.controller.controllerState;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.ColorChoiceAction;

/**ColorChoiceControllerState Class
 * @author Piersilvio Mancuso
 */
public class ColorChoiceControllerState extends ControllerState{

    // ------------ CONSTRUCTOR ------------

    /**ColorChoiceControllerState Constructor
     */
    public ColorChoiceControllerState() {
        this.className = "ColorChoiceControllerState";
    }


    // --------- ACTION CREATION ----------------

    /**Create a Color Choice Action
     * @param string is the string with which an Action will be created
     * @return a Color Choice Action object
     */
    @Override
    public Action createAction(String string) {
        return new ColorChoiceAction(string);
    }
}
