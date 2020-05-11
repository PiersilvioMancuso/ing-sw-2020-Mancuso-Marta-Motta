package it.polimi.ingsw.controller.controllerState;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.WorkerSetupAction;

/**Worker Setup State
 * @author Piersilvio Mancuso
 */
public class WorkerSetupControllerState extends ControllerState{


    // ------------------ CONSTRUCTOR ---------------

    /** WorkerSetupControllerState Constructor
     */
    public WorkerSetupControllerState() {
        this.className = getClass().getSimpleName();
    }

    /**Create a Worker Setup Action
     * @param string is the string with which the action will be created
     * @return a WorkerSetupAction object
     */
    @Override
    public Action createAction(String string) {
        return new WorkerSetupAction(string);
    }
}
