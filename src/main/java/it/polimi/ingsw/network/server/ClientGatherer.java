package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ClientGatherer Class
 * @author Mattia Marta
 */
public class ClientGatherer extends Thread{

    private final Server server;
    private ServerSocket serverSocket;


    // --------------- CONSTRUCTOR ----------------

    /**ClientGatherer Constructor
     * @param port is the port of the Server's Socket
     * @param server is the Server
     */
    public ClientGatherer(int port, Server server){
        this.server = server;

        try {
            this.serverSocket = new ServerSocket(port);
        }catch (IOException e){
            System.out.println("Cannot Create the Server Socket: please check if Network Connection is On");
        }
    }

    // ------------------ THREAD EXECUTION --------------------

    /**Accept the connections and assigns a Virtual Client to each of them*/
    @Override
    public void run() {

        while (true){
            Socket newClient;

            try{
                newClient = serverSocket.accept();
                VirtualClient client = new VirtualClient(server, newClient);

                server.addClient(client);

            }catch (NullPointerException e){
                System.out.println("It was impossible to create the socket.\nPlease check if the Network Port 1888 is already in use");
                System.exit(-1);
            }

            catch (IOException e){
                System.out.println("There was a problem during a client Connection");
            }
        }
    }
}
