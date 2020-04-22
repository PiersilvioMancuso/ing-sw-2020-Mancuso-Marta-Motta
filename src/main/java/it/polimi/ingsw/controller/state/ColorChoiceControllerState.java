package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.ColorChoiceAction;

/**Color Choice Controller State Class
 * @author Piersilvio Mancuso
 */
public class ColorChoiceControllerState extends ControllerState{

    /**Create a Color Choice Action
     * @param string is the string with which an Action will be created
     * @return a Color Choice Action object
     */
    @Override
    public Action createAction(String string) {
        return new ColorChoiceAction(string);
    }
}
