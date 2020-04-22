package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.GodInGameChoiceAction;

/**God In Game Choice Controller State Class
 * @author Piersilvio Mancuso
 */
public class GodInGameChoiceState extends ControllerState{

    /**Create a God In Game Choice Action
     * @param string is the string with which an Action will be created
     * @return a God in Game Choice Action
     */
    @Override
    public Action createAction(String string) {
        return new GodInGameChoiceAction(string);
    }
}
