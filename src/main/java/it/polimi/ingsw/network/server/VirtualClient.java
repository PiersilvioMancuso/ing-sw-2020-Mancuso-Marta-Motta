package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.controllersMessages.Nack;
import it.polimi.ingsw.messages.controllersMessages.Response;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.view.Command;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * VirtualClient Class
 * @author Mattia Marta
 */
public class VirtualClient implements Sender<Message>, Runnable{
    private Server server;
    private final Socket clientSocket;
    private String userName = null;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;


    // ------------------- CONSTRUCTOR ----------------------


    /**VirtualClient Constructor
     * @param server is the server to which send the actions received
     * @param clientSocket is the socket thanks which the server can send messages to each client
     */
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

    // ------------------- GETTER --------------------------

    /**Username Getter
     * @return the username of the virtualClient, thanks which the send method can have effect
     */
    public String getUserName() {
        return userName;
    }


    /**ClientSocket Getter
     * @return the Socket with which the virtualClient send and receive messages or Action
     */
    public Socket getClientSocket() {
        return clientSocket;
    }

    /**OutputStream Getter
     * @return the Object Output Stream thanks which virtualClient can send Messages
     */
    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    /**Server Getter
     * @return the Server thanks which the action received can have effect
     */
    public Server getServer() {
        return server;
    }

    /**InputStream Getter
     * @return the InputStream where the VirtualClient Receive Objects
     */
    public ObjectInputStream getInputStream() {
        return inputStream;
    }


    // ------------------- SETTER --------------------------

    /**Server Setter
     * @param server is the Server thanks which the action received can have effect
     */
    public void setServer(Server server) {
        this.server = server;
    }

    /**OutputStream Setter
     * @param outputStream is the Object Output Stream thanks which virtualClient can send Messages
     */
    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**InputStream Setter
     * @param inputStream is the InputStream where the VirtualClient Receive Objects
     */
    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }


    /**
     * Setter for the username
     * @param userName is the new username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }


    // ------------------- LIFECYCLE -----------------------

    /**Set the virtualClient in Listening for all his lifecycle*/
    @Override
    public void run() {

        //Receive Data From the inputStream
        try {
            while (true) {
                receiveData();
            }
        }
        // If there is any problem that occur on the Socket
        catch (SocketException e){

            //If the username is the END NAME the connection will be closed
            if (this.getUserName().equals(RemoteController.getENDNAME())){
                closeConnection();
            }

            //Else if the game is not already Started the user will be removed from the Game
            else if (!server.getRemoteController().isGameStarted()){
                server.getRemoteController().getPlayerList().remove(server.getRemoteController().getUserFromUsername(this.userName));

                //If playerList is not empty, the playerList will be set to the modelGame
                if (server.getRemoteController().getPlayerList().size() > 0 && server.getRemoteController().getModelGame() != null) {
                    server.getRemoteController().getModelGame().setUserList(server.getRemoteController().getPlayerList());
                }
                return;
            }

            //Else if the game is Already Started
            else {

                //The file will be replaced by a new Data File
                server.setRemoteController(new RemoteController(server));
                server.getRemoteController().resetData();

                resetServerClients();
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

        closeConnection();

    }



    // ------------------- UTILITY ------------------------

    /**Closes all the connections with the server if any connection problem occurs by this virtualClient Connection
     */
    public void resetServerClients(){

        //Each client will be removed by the connection
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
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }


    /**Receive Data from the InputStream and send it to the Server
     * @throws IOException if any problem reading the stream occurs
     * @throws ClassNotFoundException if it's impossible to know how to deserialize the object
     */
    public void receiveData() throws IOException, ClassNotFoundException{
        Object data = inputStream.readObject();
        if (data != null) {
            Action action = (Action) data;
            this.userName = action.getUsername();
            server.receive(action);
        }
    }


    /**Close the Socket and Remove the VirtualClient from the list of virtualClient that's on the Server
     */
    public void closeConnection(){
        try {
            clientSocket.close();
            server.removeClient(this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
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

            else if (message.getClassName().contains("Ack")  && !((Response) message).getUsername().equals(userName)){
                ;
            }

            else {

                if (message.getClassName().contains("Nack") && ((Nack)message).getCommand().equals(Command.REGISTER)){
                    this.userName = RemoteController.getENDNAME();}

                this.outputStream.writeObject(message);
                this.outputStream.reset();

            }

        } catch (SocketException e){
            ;
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
