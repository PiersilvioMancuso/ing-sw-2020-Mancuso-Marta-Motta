package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.messages.Message;
import it.polimi.ingsw.model.messages.controllersMessages.EndSending;
import it.polimi.ingsw.model.messages.controllersMessages.Response;
import it.polimi.ingsw.view.Command;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Virtual client
 * @author Mattia
 */
public class VirtualClient implements Sender<Message>, Runnable{
    private Server server;
    private final Socket clientSocket;
    private String userName = null;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;



    public VirtualClient(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        try {
            this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }



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

    public Socket getClientSocket() {
        return clientSocket;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
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
     * @param message is the generic object sent
     */
    @Override
    public synchronized void send(Message message) {
        try {
            if (message.getClassName().contains("End")){
                this.outputStream.writeObject(message);
                notify();
            }

            else if (message == null){
                return;
            }

            else if (message.getClassName().contains("Ack")  && !((Response) message).getUsername().equals(userName)){
                return;
            }
            else {


                this.outputStream.writeObject(message);
                this.outputStream.reset();
                return;

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


            while (true) {
                Object data = inputStream.readObject();
                if (data != null) {
                    Action action = (Action) data;
                    this.userName = action.getUsername();
                    server.receive(action);
                }
            }
        }catch (SocketException e){
                server.setRemoteController(new RemoteController(server));
                for (VirtualClient client : server.getVirtualClientList()){
                    if (!client.equals(this)){
                        try {
                            client.clientSocket.close();

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            try {
                clientSocket.close();
                server.setVirtualClientList(new ArrayList<VirtualClient>());
            } catch (IOException ioException) {
                ioException.printStackTrace();
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
