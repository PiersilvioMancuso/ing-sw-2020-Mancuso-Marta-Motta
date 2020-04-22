package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.GodChoiceAction;

/**God Choice Controller State Class
 * @author Piersilvio Mancuso
 */
public class GodChoiceState extends ControllerState{

    /**Create a God Choice Action
     * @param string is the string with which an Action will be created
     * @return a God Choice Action object
     */
    @Override
    public Action createAction(String string) {
        return new GodChoiceAction(string);
    }
}
