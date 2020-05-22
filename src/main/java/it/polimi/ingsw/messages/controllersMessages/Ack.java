package it.polimi.ingsw.messages.controllersMessages;

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
        this.className = super.className +"-Ack-" + controllerState;
    }


    // ------------ GETTER ----------------

    /**Ack Getter
     * @return the Controller State that will be set to Controller Client
     */
    public ControllerState getControllerState() {
        return controllerState;
    }


    /**Command Setter
     * @param command is the Command that will be set to the View
     */
    @Override
    public void setCommand(Command command) {
        super.setCommand(command);
    }


    /**Username Setter
     * @param username is the username to which the message is valid
     */
    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }


    /**ControllerState Setter
     * @param controllerState is the state that will be sent to the Remote Controller
     */
    public void setControllerState(ControllerState controllerState) {
        this.controllerState = controllerState;
    }
}

