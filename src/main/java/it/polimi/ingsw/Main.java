package it.polimi.ingsw;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.Server;

import java.util.Scanner;

/**
 * Main Class
 * @author Piersilvio Mancuso
 */
public class Main {

    /**Game Class runner
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select between Client and Server:\n0 - Client\n1 - Server");


        String value = scanner.nextLine();

        if (value.matches("[a-zA-Z]+")){
            if (value.toUpperCase().trim().equals("CLIENT")) new Client();

            else {
                Server server = new Server();

                while (true){
                    server.getClientGatherer().run();
                }
            }
        }

        else if (value.matches("[0-9]")){
            if (Integer.parseInt(value) == 0) new Client();
            else {
                Server server = new Server();

                while (true){
                    server.getClientGatherer().run();
                }
            }
        }

    }
}
