package it.polimi.ingsw.network.server;

/**
 * Broadcast
 * @author Mattia
 */
public interface Broadcast {
    /**
     * Sends a message to all the clients
     * @param obj is the object that will be sent containing the information.
     */
    public void broadcast(Object obj);

}
