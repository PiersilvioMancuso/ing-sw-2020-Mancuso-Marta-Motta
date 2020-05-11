package it.polimi.ingsw.network.server;

import java.io.IOException;

/**
 * Sender
 * @param <T> is the generic type of object
 * @author Mattia
 */
public interface Sender<T> {

    /**
     * Method that's used to send objects
     * @param obj is the generic object sent
     * @throws IOException
     */
    public void send(T obj) throws IOException;
}
