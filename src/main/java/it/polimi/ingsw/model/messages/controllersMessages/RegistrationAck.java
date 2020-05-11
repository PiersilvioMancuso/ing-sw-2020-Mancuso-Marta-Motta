package it.polimi.ingsw.model.messages.controllersMessages;

import it.polimi.ingsw.view.Command;

/**Registration Ack Class
 * @author Piersilvio Mancuso
 */
public class RegistrationAck extends Response{

    // ------------------ CONSTRUCTOR ---------------


    /**RegistrationAck Constructor
     * @param username is the username to which the Response can be used
     * @param command  is the command that Controller Client will set to the View
     */
    public RegistrationAck(String username, Command command) {
        super(username, command);
        this.className = getClass().getSimpleName();
    }
}
