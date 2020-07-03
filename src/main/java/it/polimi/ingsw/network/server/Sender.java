package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.SocketException;

/**Sender Interface
 * @param <T> is the generic type of object
 * @author Mattia Marta
 */
public interface Sender<T> {

    /**Method that's used to send objects
     * @param t is the generic t sent
     */
    void send(T t) throws SocketException;
}
