package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.network.server.Receiver;
import it.polimi.ingsw.network.server.Sender;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Network Handler
 * @author Mattia
 */
public class NetworkHandler implements Sender<Action>, Receiver {
    private Client client;
    private Socket socket;

    public NetworkHandler(String ipAddress, int port,  Client client) throws IOException {
        this.socket = new Socket(ipAddress,port);
        this.client = client;
    }

    /**
     * Send actions to the client
     * @param action is the action that will be executed
     */
    @Override
    public void receive(Object action) {
        client.receive(action);
    }

    /**
     *
     * @param obj
     */
    @Override
    public void send(Action obj){
        ObjectOutputStream outputStream = null;
        try {
            outputStream = (ObjectOutputStream) socket.getOutputStream();
            outputStream.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the Network Handler making it in constant listen state.
     */
    public void start(){
        while (true){
            try {
                ObjectInputStream inputStream = (ObjectInputStream) socket.getInputStream();
                Object obj = inputStream.readObject();
                receive(obj);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
