package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.ControllerClient;
import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.RegisterAction;
import it.polimi.ingsw.model.Update;
import it.polimi.ingsw.model.messages.controllersMessages.Response;
import it.polimi.ingsw.network.EndSending;
import it.polimi.ingsw.network.server.Receiver;
import it.polimi.ingsw.network.server.Sender;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.Cli;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.network.server.Server.SOCKET_PORT;

/**
 * Client
 * @author Mattia
 */
public class Client implements Receiver<Object>, Sender<Action> {
    private Socket socket;
    private ControllerClient controllerClient;
    private View view;
    private NetworkHandler networkHandler;

    private List<Object> objects;


    public Client(int value){
        objects = new ArrayList<>();

        if (value == 0){
            view = new Cli();
        }

        controllerClient = new ControllerClient(view);
        view.addController(controllerClient);
        view.run();
    }

    /**
     * Manage the receiving of the various objects.
     * If the object received is non an "EndSending" add them to the objects list
     * Otherwise it executes the commands in the list.
     * @param object is a generic object containing data.
     */
    @Override
    public void receive(Object object) {
        if (!(object instanceof EndSending)){
            objects.add(object);
        }
        else {
            executeObjects();
        }

    }

    /**
     * Clears the objects list
     */
    public void clearObjects(){
        objects = new ArrayList<>();
    }

    /**
     * Register the user pairing him with a Network Handler.
     * @param obj is a generic object containing data.
     */
    @Override
    public void send(Action obj) {
        if (obj instanceof RegisterAction){
            try {
                networkHandler = new NetworkHandler(((RegisterAction) obj).getIpAddress(), SOCKET_PORT, this);
                networkHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * Execute the various operations based on the type of object
     * If the object is an Update, set the changes on the view
     * otherwise it's a Response used as notify for the client-side controller
     */
    public void executeObjects(){
        for (Object obj : objects){
            if (obj instanceof Update){
                ((Update) obj).setChanges(view);
            }
            else if (obj instanceof Response){
                try {
                    controllerClient.notifyResponse((Response) obj);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        clearObjects();
        controllerClient.run();
    }


    public static void main(String[] args) {
        Client client = new Client(0);

    }
}
