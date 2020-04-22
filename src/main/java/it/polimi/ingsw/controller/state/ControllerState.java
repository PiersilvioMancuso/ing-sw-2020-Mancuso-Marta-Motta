package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.action.Action;

/**Abstract Controller State Class
 * @author Piersilvio Mancuso
 */
abstract public class ControllerState {

    /**Abstract Create Action
     * @param string is the string with which an Action will be created
     * @return the Action
     */
    abstract public Action createAction(String string);
}
