package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Client Gatherer
 * @author Mattia
 */
public class ClientGatherer extends Thread{
    private Server server;
    //private Receiver receiver;
    private ServerSocket serverSocket;

    public ClientGatherer(int port, Server server){
        this.server = server;

        try {
            this.serverSocket = new ServerSocket(port);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Accept the connection and assigns a Virtual Client to it
     */
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
