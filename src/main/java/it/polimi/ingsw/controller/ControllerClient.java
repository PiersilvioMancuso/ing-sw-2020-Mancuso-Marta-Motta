package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.controller.state.*;
import it.polimi.ingsw.model.event.response.*;
import it.polimi.ingsw.view.*;

/**Controller Client
 * @author Piersilvio Mancuso
 */
public class ControllerClient {
    private View view;
    private  String user;
    private ControllerState controllerState;
    private boolean wait;

    public ControllerClient(View view) {
        wait = false;
        this.view = view;
        this.user = "";
        this.controllerState = new RegisterState();
    }

    /**Get the View changes, create the Action following the state and
     * send it to the Remote Controller
     */
    public void notifyControllerAction() {
        String string = view.getUserData();
        if (!wait){
            wait = true;
            if (string.contains("user")) {
                this.user = "";
            }
            if (user != null) string = user + ";" + string;
            Action action = controllerState.createAction(string);
            this.user = action.getUsername();
            //send(action);
        }

    }

    /**Set the Controller State
     * @param response is the response belong to Action Execution
     */
    public void notifyResponse(Response response){
        if (response instanceof Ack){
            this.controllerState = ((Ack) response).getControllerState();
        }
    }




    public void send(Object object, Action action){
        //object.send(action);
    }


}