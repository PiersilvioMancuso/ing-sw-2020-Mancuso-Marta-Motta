package it.polimi.ingsw.controller.controllerState;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.RegistrationAction;


/**RegisterControllerState Class
 * @author Piersilvio Mancuso
 */
public class RegisterControllerState extends ControllerState{

    // ------------------ CONSTRUCTOR ---------------

    /** RegisterControllerState Constructor
     */
    public RegisterControllerState() {
        this.className = "RegisterControllerState";
    }


    // --------- ACTION CREATION ----------------

    /**Create the Action RegistrationAction, that will be executed by the Remote Controller
     * @param string is the String passed by the Controller Client to create the Action
     * @return a Registration Action Object
     */
    @Override
    public Action createAction(String string) {

        return new RegistrationAction(string);
    }
}
