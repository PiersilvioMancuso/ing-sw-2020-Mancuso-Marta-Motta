package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.action.Action;

import java.io.Serializable;

/**
 * Receiver
 * @param <T> is the generic type of object
 * @author Mattia
 */
public interface Receiver<T>{

    /**
     * Method used to receive objects
     * @param t is the generic t received
     */
    void receive(T t);
}
