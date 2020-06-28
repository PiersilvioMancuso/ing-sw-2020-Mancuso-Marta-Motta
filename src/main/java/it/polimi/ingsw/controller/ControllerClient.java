package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.controller.controllerState.*;
import it.polimi.ingsw.messages.controllersMessages.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.*;


/**ControllerClient Class
 * @author Piersilvio Mancuso
 */
public class ControllerClient {
    private View view;
    private  String user;
    private ControllerState controllerState;
    private boolean wait;
    private boolean registered;
    private Response response;
    private Client client;



    // ---------------- CONSTRUCTOR --------------

    /**ControllerClient Constructor
     * @param client is the client from which Controller Client will be notified
     */
    public ControllerClient(Client client) {


        this.view = new Cli();
        view.addController(this);
        this.registered = false;
        this.wait = false;
        this.user = "";
        this.controllerState = new RegisterControllerState();
        this.client = client;
    }

    // ----------------- GETTER -----------------

    /**View Getter
     * @return the view controlled by the Controller Client
     */
    public View getView() {
        return view;
    }

    /**Username Getter
     * @return the username of the Client
     */
    public String getUser() {
        return user;
    }

    /**ControllerState Getter
     * @return the state of the controller client
     */
    public ControllerState getControllerState() {
        return controllerState;
    }

    /**Wait Getter
     * @return the boolean value that says if the action can be sent
     */
    public boolean isWait() {
        return wait;
    }

    /**Registration Getter
     * @return the boolean value that say if the user is Registered to the Game
     */
    public boolean isRegistered() {
        return registered;
    }

    /**Response Getter
     * @return the response that arrived from the server
     */
    public Response getResponse() {
        return response;
    }


    /**Client Getter
     * @return the Client thanks which tha action will be sent
     */
    public Client getClient() {
        return client;
    }

    // --------------- SETTER --------------------

    /**Response Setter
     * @param response is the response that will be set to the Controller Client
     */
    public void setResponse(Response response) {
        this.response = response;
        view.setCommand(response.getCommand());
    }

    /**View Setter
     * @param view is the view that will be set to ControllerClient
     */
    public void setView(View view) {
        this.view = view;
    }

    /**User Setter
     * @param user is the user that will be set to ControllerClient
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**ControllerState Setter
     * @param controllerState is the State that will be set to the ControllerClient to choose the action that has to be created
     */
    public void setControllerState(ControllerState controllerState) {
        this.controllerState = controllerState;
    }

    /**Wait Setter
     * @param wait is the boolean value that say if the action can be sent
     */
    public void setWait(boolean wait) {
        this.wait = wait;
    }

    /**Registered Setter
     * @param registered is the value that say if the registration have been successfully completed
     */
    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    /**Client Setter
     * @param client is the client that will be set to ControllerClient
     */
    public void setClient(Client client) {
        this.client = client;
    }

    // -------------- CONTROLLER ACTION ------------

    /**Get the View changes, create the Action following the state and
     * send it to the Remote Controller
     */
    public void notifyControllerAction() {
        String string = view.getUserData();
        if (!wait){
            wait = true;
            if (controllerState.getClassName().contains("RegisterControllerState")) {
                this.user = "";
            }
            if (!user.equals("")) string = "username="+ user + ";" + string;
            Action action = controllerState.createAction(string);
            this.user = action.getUsername();
            send(action);
        }

    }


    /**Call to the View the method to update or print the Board*/
    public void printView(){
        view.printBoard();
    }


    /**Execute the view phase*/
    public void viewRunner(){
        if (!wait)
            view.run();
    }


    /**Send to the Client the action created
     * @param action is the action that will be sent into the server
     */
    public void send(Action action){
        client.send(action);
    }





    /**If the response is related to the user:
     *  - 1) If the response is an Ack, ControllerClient will set his ControllerState, will set View Command and will run it
     *  - 2) If the response is a Nack, ControllerClient will set the Command to the View and will run View after Error Message Printing
     */
    public void notifyResponse()  {


        //Check if the response is related to the user
        if (response.getUsername().equals(user)) {


            //If receive a registrationAck
            if (response.getClassName().contains("RegistrationAck")  && !registered){
                registered = true;
                view.printError("Connected and Registered to the Lobby, please wait until the lobby is full");
            }

            //If the response is an Ack, ControllerClient will set his ControllerState, will set View Command and will run it
            else if (response.getClassName().contains("Ack") && registered) {
                wait = false;
                this.controllerState = ((Ack) response).getControllerState();
                view.setCommand((response).getCommand());
            }

            //If the response is a Nack, ControllerClient will set the Command to the View and will run View after Error Message Printing
            else if (response.getClassName().contains("Nack")) {

                wait = false;
                view.setCommand(response.getCommand());
                view.printError(((Nack) response).getMessage());


            }
        }

    }


}