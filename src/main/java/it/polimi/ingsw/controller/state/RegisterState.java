package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.RegisterAction;


/**Register Controller State
 * @author Piersilvio Mancuso
 */
public class RegisterState extends ControllerState{

    /**Create the Action RegisterAction, that will be executed by the Remote Controller
     * @param string is the String passed by the Controller Client to create the Action
     * @return a Registration Action Object
     */
    @Override
    public Action createAction(String string) {

        Action action = new RegisterAction(string);
        return action;
    }
}
