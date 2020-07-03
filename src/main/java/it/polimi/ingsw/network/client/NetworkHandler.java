package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.controllersMessages.Nack;
import it.polimi.ingsw.network.server.Receiver;
import it.polimi.ingsw.network.server.Sender;
import it.polimi.ingsw.view.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * NetworkHandler Class
 * @author Mattia Marta
 */
public class NetworkHandler implements Sender<Action>, Receiver<Message> {
    private final Client client;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private String username;



    // ---------------- CONSTRUCTOR -----------------

    /**Network Handler Constructor
     * @param ipAddress is the address of the Server to connect with
     * @param port is the port of the socket where the server is listening
     * @param client is the Client where to send the Messages received
     */
    public NetworkHandler(String ipAddress, int port,  Client client) {
        this.client = client;
        try {
            this.socket = new Socket(ipAddress,port);
            this.socket.setSoTimeout(5*1000);

            outputStream = new ObjectOutputStream(socket.getOutputStream()) ;

            inputStream = new ObjectInputStream(socket.getInputStream()) ;

        }catch (SocketTimeoutException e){
            client.getView().printError("Connection TimedOut, please check if you are connected");
        }

        catch (SocketException e){
            client.getView().printError("Connection Refused by the Server: check if the Server is On");

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
        if (message.getClassName().contains("Nack")){
            if (((Nack)message).getCommand().equals(Command.REGISTER)  && client.getControllerClient().isRegistered()) return;
        }

        client.receive(message);
    }


    // --------------- SENDER -------------------

    /**Send the actions into the socket Output Stream
     * @param action is the action that will be sent to Remote Controller
     */
    @Override
    public void send(Action action)  {
        client.clearMessageList();


        if (outputStream != null){
            this.username = action.getUsername();
            try {
                this.socket.setSoTimeout(5*1000);
                outputStream.writeObject(action);
                outputStream.reset();
            }
            catch (SocketException e){
                client.getView().printError("Connection interrupted by an other player, so the game is Over");

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else client.getView().printError("Server is not Running");

    }




    // ------------------ LISTENER -------------------

    /**NetworkHandler LifeCycle
     */
    public void start(){
        //If the connection to the server is not active: Do Nothing
        if (inputStream == null) return;

        while (true){

            //Receive all Messages from the Server and they will be forwarded to the client
            try {

                Message message = (Message) inputStream.readObject();
                if (message!= null){
                    this.socket.setSoTimeout(180*1000);
                    if (message.getClassName().contains("End")){
                        client.executeMessages();
                    }
                    else receive(message);

                }

            } catch (SocketException e){
                return;

            } catch (IOException e) {
                return ;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            }
        }
    }
}
