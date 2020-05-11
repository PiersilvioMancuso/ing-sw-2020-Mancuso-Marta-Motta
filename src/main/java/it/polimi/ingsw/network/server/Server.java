package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.action.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Server
 * @author Mattia
 */
public class Server implements Receiver<Action>, Broadcast {
    public final static int SOCKET_PORT = 8888;

    private ClientGatherer clientGatherer;
    private List<VirtualClient> virtualClientList;
    private RemoteController remoteController;

    public List<VirtualClient> getVirtualClientList(){
        return virtualClientList;
    }

    public Server (){
        virtualClientList = new ArrayList<>();
        remoteController = new RemoteController();
        clientGatherer = new ClientGatherer(SOCKET_PORT, this);
    }


    /**
     * Getter for the client gatherer
     * @return the client gatherer
     */
    public ClientGatherer getClientGatherer() {
        return clientGatherer;
    }

    /**
     * Setter for the client gatherer
     * @param clientGatherer
     */
    public void setClientGatherer(ClientGatherer clientGatherer) {
        this.clientGatherer = clientGatherer;
    }

    /**
     * Setter for the Virtual Client list
     * @param virtualClientList is the list of Virtual Clients
     */
    public void setVirtualClientList(List<VirtualClient> virtualClientList) {
        this.virtualClientList = virtualClientList;
    }

    /**
     * Getter for the remote controller
     * @return the remote controller
     */
    public RemoteController getRemoteController() {
        return remoteController;
    }

    /**
     * Add a Virtual Client to the list
     * @param client is the Virtual Client
     */
    public void addClient(VirtualClient client){
        this.virtualClientList.add(client);
    }

    /**
     * Remove the selected Virtual Client from the list
     * @param client is the Vrtual Client that is going to be removed
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
     * @param obj is the object that will be sent containing the information.
     */
    @Override
    public void broadcast(Object obj) {
        for(VirtualClient client : this.virtualClientList){
            client.send(obj);
        }
    }


    //--------------------------------------------------
    public static void main(String[] args)
    {
        Server server = new Server();

        while (true){
            server.clientGatherer.run();
        }

    }

}
