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
     * Method used to receive actions
     * @param action is the generic action received
     */
    void receive(T action);
}
