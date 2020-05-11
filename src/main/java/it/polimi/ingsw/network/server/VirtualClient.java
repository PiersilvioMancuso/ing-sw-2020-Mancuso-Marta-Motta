package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.messages.controllersMessages.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Virtual client
 * @author Mattia
 */
public class VirtualClient implements Sender<Object>, Runnable{
    private Server server;
    private Socket clientSocket;
    private String userName;

    public VirtualClient(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        Thread thread = new Thread(this);

        thread.start();
    }

    /**
     * Getter for the username
     * @return the username of the player
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for the username
     * @param userName is the new username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Selective send, it forward the object only if the username math
     * @param obj is the generic object sent
     */
    @Override
    public synchronized void send(Object obj) {
        try {
            if (obj instanceof Response && !((Response) obj).getUsername().equals(userName)){
                return;
            }
            else {
                ObjectOutputStream output = (ObjectOutputStream) clientSocket.getOutputStream();
                output.writeObject(obj);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Send the action to the server if it is not null
     */
    @Override
    public void run() {
        try {
            ObjectInputStream input = (ObjectInputStream) clientSocket.getInputStream();
            while (true){
                Object data = input.readObject();
                if (data != null){
                    Action action = (Action) data;
                    this.userName = action.getUsername();
                    server.receive(action);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            clientSocket.close();
            server.removeClient(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
