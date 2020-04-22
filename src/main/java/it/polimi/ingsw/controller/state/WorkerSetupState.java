package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.WorkerSetupAction;

/**Worker Setup State
 * @author Piersilvio Mancuso
 */
public class WorkerSetupState extends ControllerState{

    /**Create a Worker Setup Action
     * @param string is the string with which the action will be created
     * @return a WorkerSetupAction object
     */
    @Override
    public Action createAction(String string) {
        return new WorkerSetupAction(string);
    }
}
