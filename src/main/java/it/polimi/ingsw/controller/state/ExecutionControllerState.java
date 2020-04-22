package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.ExecuteControllerAction;

/**Execution Controller State
 * @author Piersilvio Mancuso
 */
public class ExecutionControllerState extends ControllerState{

    /**Create an ExecuteControllerAction
     * @param string is the string with which an ExecuteControllerAction object will be created
     * @return an ExecuteControllerAction
     */
    @Override
    public Action createAction(String string) {
        return new ExecuteControllerAction(string);
    }
}
