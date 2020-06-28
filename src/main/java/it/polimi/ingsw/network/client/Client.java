package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.ControllerClient;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.RegistrationAction;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.controllersMessages.Response;
import it.polimi.ingsw.messages.modelViewMessages.Update;
import it.polimi.ingsw.network.server.Receiver;
import it.polimi.ingsw.network.server.Sender;
import it.polimi.ingsw.view.View;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.network.server.Server.SOCKET_PORT;


/**Client Class
 * @author Mattia Marta
 */
public class Client implements Receiver<Message>, Sender<Action> {
    private Socket socket;
    private ControllerClient controllerClient;
    private View view;
    private NetworkHandler networkHandler;

    private List<Message> messageList;



    // -------------- CONSTRUCTOR -------------------

    /**Client Constructor
     */
    public Client(){
        messageList = new ArrayList<>();

        controllerClient = new ControllerClient(this);
        view = controllerClient.getView();
        view.addController(controllerClient);
        view.run();
    }


    // --------------- GETTER -----------------------

    /**MessageList Getter
     * @return the list of all messages received
     */
    public List<Message> getMessageList() {
        return messageList;
    }


    /**Socket Getter
     * @return the Socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**ControllerClient Getter
     * @return the ControllerClient
     */
    public ControllerClient getControllerClient() {
        return controllerClient;
    }

    /**View Getter
     * @return the View, that can be CLI or GUI
     */
    public View getView() {
        return view;
    }


    /**NetworkHandler Getter
     * @return the NetworkHandler
     */
    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }



    // --------------- SETTER ---------------------

    /**Socket Setter
     * @param socket is the Socket that will thanks which there will be a connection to the Server
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**ControllerClient Setter
     * @param controllerClient is the controllerClient that will manage the action between View and RemoteController
     */
    public void setControllerClient(ControllerClient controllerClient) {
        this.controllerClient = controllerClient;
    }

    /**View Setter
     * @param view is the User Interface
     */
    public void setView(View view) {
        this.view = view;
    }

    /**NetworkHandler Setter
     * @param networkHandler is the Connection handler
     */
    public void setNetworkHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }



    // ----------- RECEIVER ----------------

    /**Add the object received into the objects list
     * @param t is the object that will be added into the list
     */
    @Override
    public void receive(Message t) {
        messageList.add(t);
    }



    // ------------------ SENDER ------------------

    /**
     * Register the user pairing him with a Network Handler.
     * @param action is a generic object containing data.
     */
    @Override
    public void send(Action action) {


        if (action.getClassName().contains("Registration") ){

            networkHandler = new NetworkHandler(((RegistrationAction) action).getIpAddress(), SOCKET_PORT, this);
            networkHandler.send(action);
            networkHandler.start();



        }
        else networkHandler.send(action);

    }

    // --------------- UTILITIES --------------

    /**
     * Execute the various operations based on the type of object
     * If the object is an Update, set the changes on the view
     * otherwise it's a Response used as notify for the client-side controller
     */
    public void executeMessages(){
        if (messageList.size()>0){
            for (Message message : messageList){
                if (message.getClassName().contains("Update")){
                    ((Update) message).setChanges(view);
                }
                else if (message.getClassName().contains("Response") ){
                    controllerClient.setResponse((Response) message);
                    controllerClient.notifyResponse();
                }
            }
            clearMessageList();
            controllerClient.printView();
            controllerClient.viewRunner();
        }


    }


    /**
     * Clears the messageList
     */
    public void clearMessageList(){
        messageList.clear();
    }


    // ------------ MAIN --------------------


    public static void main(String[] args) {
        new Client();

    }
}
