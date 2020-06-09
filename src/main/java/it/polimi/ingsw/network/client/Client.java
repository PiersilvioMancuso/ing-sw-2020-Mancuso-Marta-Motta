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
     * @param value is the parameter thanks which it will be created a CLI or a GUI view
     */
    public Client(int value){
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


    public Socket getSocket() {
        return socket;
    }


    public ControllerClient getControllerClient() {
        return controllerClient;
    }


    public View getView() {
        return view;
    }


    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }



    // --------------- SETTER ---------------------

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setControllerClient(ControllerClient controllerClient) {
        this.controllerClient = controllerClient;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setNetworkHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
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
        else {
            controllerClient.printView();
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
        Client client = new Client(0);

    }
}
