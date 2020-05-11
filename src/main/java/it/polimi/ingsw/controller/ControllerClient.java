package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.controller.controllerState.*;
import it.polimi.ingsw.model.messages.controllersMessages.*;
import it.polimi.ingsw.view.*;


/**Controller Client
 * @author Piersilvio Mancuso
 */
public class ControllerClient {
    private View view;
    private  String user;
    private ControllerState controllerState;
    private boolean wait;
    private boolean registered;
    private Response response;


    /**ControllerClient Constructor
     * @param view is the view from which Controller Client will be notified
     */
    public ControllerClient(View view) {
        this.registered = false;
        this.wait = false;
        this.view = view;
        this.user = "";
        this.controllerState = new RegisterControllerState();
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

    /**If the response is related to the user:
     *  - 1)If the response is an Ack, ControllerClient will set his ControllerState, will set View Command and will run it
     *  - 2)If the response is a Nack, ControllerClient will set the Command to the View and will run View after Error Message Printing
     */
    public void notifyResponse() throws InterruptedException {
        //Check if the response is related to the user
        if (response.getUsername().equals(user)) {

            //If receive a registrationAck
            if (response.getClassName().contains("RegistrationAck")  && !registered){
                registered = true;
            }

            //If the response is an Ack, ControllerClient will set his ControllerState, will set View Command and will run it
            else if (response.getClassName().contains("Ack")) {
                wait = false;
                this.controllerState = ((Ack) response).getControllerState();
                view.setCommand(response.getCommand());

            }

            //If the response is a Nack, ControllerClient will set the Command to the View and will run View after Error Message Printing
            if (response.getClassName().contains("Nack")) {
                wait = false;
                view.setCommand(response.getCommand());
                view.printError(((Nack) response).getMessage());
                Thread.sleep(1500l);

            }
        }



    }

    public void run(){
        view.run();
    }




    public void send(Object object, Action action){
        //object.send(action);
    }


}