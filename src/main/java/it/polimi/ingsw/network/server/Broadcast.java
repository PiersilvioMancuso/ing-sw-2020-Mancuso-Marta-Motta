package it.polimi.ingsw.network.server;

/**
 * Broadcast
 * @author Mattia Marta
 */
public interface Broadcast<T> {
    /**
     * Sends a message to all the clients
     * @param t is the object that will be sent containing the information.
     */
    void broadcast(T t);

}
