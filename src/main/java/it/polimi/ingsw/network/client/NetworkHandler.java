package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.messages.Message;
import it.polimi.ingsw.model.messages.controllersMessages.Response;
import it.polimi.ingsw.network.server.Receiver;
import it.polimi.ingsw.network.server.Sender;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * NetworkHandler Class
 * @author Mattia Marta
 */
public class NetworkHandler implements Sender<Action>, Receiver<Message> {
    private Client client;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private String username;



    // ---------------- CONSTRUCTOR -----------------

    /**Network Handler Constructor
     * @param ipAddress is the address of the Server to connect with
     * @param port is the port of the socket where the server is listening
     * @param client is the Client where to send the Messages received
     * @throws IOException if any problem occurs during Input or Output Stream Creation
     */
    public NetworkHandler(String ipAddress, int port,  Client client) {
        this.client = client;
        try {
            this.socket = new Socket(ipAddress,port);
            outputStream = new ObjectOutputStream(socket.getOutputStream()) ;

            inputStream = new ObjectInputStream(socket.getInputStream()) ;




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // --------------- RECEIVER ------------------

    /**
     * Send actions to the client
     * @param message is the action that will be executed
     */
    @Override
    public void receive(Message message) {
        client.receive(message);
    }


    // --------------- SENDER -------------------

    /**Send the actions into the socket Output Stream
     * @param action is the action that will be sent to Remote Controller
     */
    @Override
    public void send(Action action){
        this.username = action.getUsername();
        try {
            outputStream.writeObject(action);
            outputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    // ------------------ LISTENER -------------------

    public void start(){

        while (true){
            try {
                Message message = (Message) inputStream.readObject();
                if (message!= null){
                    System.out.println(message);
                    if (message.getClassName().contains("End")){
                        client.executeMessages();
                    }
                    else receive(message);

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
