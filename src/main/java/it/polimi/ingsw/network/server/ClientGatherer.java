package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ClientGatherer Class
 * @author Mattia Marta
 */
public class ClientGatherer extends Thread{

    private Server server;
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
            e.printStackTrace();
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

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
