package it.polimi.ingsw.model.event.response;

import it.polimi.ingsw.controller.state.ControllerState;

/**Ack Response Class
 * @author Piersilvio Mancuso
 */
public class Ack extends Response{

    protected ControllerState controllerState;

    /**Ack Constructor
     * @param state is the state that will be set to Controller Client
     */
    public Ack(ControllerState state){
        this.controllerState = state;
    }

    /**Ack Getter
     * @return the Controller State that will be set to Controller Client
     */
    public ControllerState getControllerState() {
        return controllerState;
    }
}
