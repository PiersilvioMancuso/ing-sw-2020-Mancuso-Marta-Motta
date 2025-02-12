package it.polimi.ingsw.messages.controllersMessages;

import it.polimi.ingsw.view.Command;

/**Registration Ack Class
 * @author Piersilvio Mancuso
 */
public class RegistrationAck extends Ack{

    // ------------------ CONSTRUCTOR ---------------


    /**RegistrationAck Constructor
     * @param username is the username to which the Response can be used
     * @param command  is the command that Controller Client will set to the View
     */
    public RegistrationAck(String username, Command command) {
        super(username, command, null);
        this.className = super.getClassName() + "-RegistrationAck";
    }
}
