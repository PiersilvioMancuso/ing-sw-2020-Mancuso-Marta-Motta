package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.controllersMessages.Nack;
import it.polimi.ingsw.view.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Server Class
 * @author Mattia Marta
 */
public class Server implements Receiver<Action>, Broadcast<Message> {

    // ------------ FIELDS -----------------
    public final static int SOCKET_PORT = 8888;
    private ClientGatherer clientGatherer;
    private List<VirtualClient> virtualClientList;
    private RemoteController remoteController;




    // ------------- CONSTRUCTOR ----------------

    /**Server Constructor
     * Initialize virtualClientList, Remote Controller and Client Gatherer
     */
    public Server (){
        virtualClientList = new ArrayList<>();
        remoteController = new RemoteController(this);
        clientGatherer = new ClientGatherer(SOCKET_PORT, this);
    }


    // ---------------- GETTER --------------

    /**ClientGatherer Getter
     * @return the ClientGatherer
     */
    public ClientGatherer getClientGatherer() {
        return clientGatherer;
    }


    /**RemoteController Getter
     * @return the remote controller
     */
    public RemoteController getRemoteController() {
        return remoteController;
    }

    /**SocketPort Getter
     * @return the Socket Port thanks which the Socket can be created
     */
    public static int getSocketPort() {
        return SOCKET_PORT;
    }

    /**VirtualClientList Getter
     * @return a list of all virtualClient connected
     */
    public List<VirtualClient> getVirtualClientList(){
        return virtualClientList;
    }


    // ---------------- SETTER --------------

    /**Setter for the client gatherer
     * @param clientGatherer is the client gatherer who will create the virtualClient
     */
    public void setClientGatherer(ClientGatherer clientGatherer) {
        this.clientGatherer = clientGatherer;
    }

    /**Setter for the Virtual Client list
     * @param virtualClientList is the list of Virtual Clients
     */
    public void setVirtualClientList(List<VirtualClient> virtualClientList) {
        this.virtualClientList = virtualClientList;
    }

    /**RemoteController Setter
     * @param remoteController is the RemoteController that will be set to the Server
     */
    public void setRemoteController(RemoteController remoteController) {
        this.remoteController = remoteController;
    }



    // ---------------- SERVER ACTION --------------

    /**Add a Virtual Client to the list
     * @param client is the Virtual Client
     */
    public void addClient(VirtualClient client){
        this.virtualClientList.add(client);
    }


    /**
     * Remove the selected Virtual Client from the list
     * @param client is the Virtual Client that is going to be removed
     */
    public void removeClient(VirtualClient client){
        this.virtualClientList.remove(client);

    }

    /**
     * Execute the action on the remote controller
     * @param action is the generic action received
     */
    @Override
    public void receive(Action action) {
        remoteController.executeAction(action);
    }



    /**
     * Send the object to all the clients
     * @param message is the object that will be sent containing the information.
     */
    @Override
    public void broadcast(Message message) {
        for(VirtualClient client : this.virtualClientList){
            client.send(message);

        }


        if (remoteController.isGameEnded() && message.getClassName().contains("EndSending")){
            List<VirtualClient> clientList = new ArrayList<>(virtualClientList);
            for (VirtualClient client : clientList){
                try {
                    client.getClientSocket().close();
                    removeClient(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            remoteController = new RemoteController(this);
        }
    }


    // --------------- MAIN -----------------

    /**Is the runnable Server's Code
     * @param args is not defined
     */
    public static void main(String[] args)
    {
        Server server = new Server();

        while (true){
            server.clientGatherer.run();
        }

    }

}
