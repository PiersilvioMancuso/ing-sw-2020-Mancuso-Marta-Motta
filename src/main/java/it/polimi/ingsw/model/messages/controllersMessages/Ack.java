package it.polimi.ingsw.model.messages.controllersMessages;

import it.polimi.ingsw.controller.controllerState.ControllerState;
import it.polimi.ingsw.view.Command;


/**Ack Response Class
 * @author Piersilvio Mancuso
 */
public class Ack extends Response{

    protected ControllerState controllerState;

    // ---------- CONSTRUCTOR ----------------

    /**Ack Constructor
     * @param username         is the username to which the Ack can be used
     * @param command          is the command that ControllerClient will set to the View
     * @param controllerState  is the state that will be set to the ControllerClient
     */
    public Ack(String username, Command command, ControllerState controllerState) {
        super(username,command);
        this.controllerState = controllerState;
        this.className = getClass().getSimpleName();
    }


    // ------------ GETTER ----------------

    /**Ack Getter
     * @return the Controller State that will be set to Controller Client
     */
    public ControllerState getControllerState() {
        return controllerState;
    }
}
