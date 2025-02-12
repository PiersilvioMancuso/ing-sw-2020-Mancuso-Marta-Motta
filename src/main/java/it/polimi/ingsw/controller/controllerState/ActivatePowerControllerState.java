package it.polimi.ingsw.controller.controllerState;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.ActivatePowerAction;
import it.polimi.ingsw.controller.action.ExecuteControllerAction;

/**ActivatePowerControllerState Class
 * @author Piersilvio Mancuso
 */
public class ActivatePowerControllerState  extends ControllerState{

    // --------- CONSTRUCTOR -------------

    /**ActivatePowerController Constructor
     */
    public ActivatePowerControllerState() {
        this.className = "ActivatePowerControllerState";
    }


    // --------- ACTION CREATION ----------------

    /**Create an ExecuteControllerAction
     * @param string is the string with which an ActivatePowerAction object will be created
     * @return an ActivatePowerAction
     */
    @Override
    public Action createAction(String string) {
        return new ActivatePowerAction(string);
    }
}
