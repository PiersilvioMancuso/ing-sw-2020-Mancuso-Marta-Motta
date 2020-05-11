package it.polimi.ingsw.model.messages.controllersMessages;

import it.polimi.ingsw.view.Command;

/**Nack Response Class
 * @author Piersilvio Mancuso
 */
public class Nack extends Response{

    protected String message;

    // ---------- CONSTRUCTOR ---------------

    /**Nack Constructor
     * @param message is the message that will be printed by view
     * @param username is the username of the Client who have to operate
     * @param command is the command that will be set to the View
     */
    public Nack(String message, String username, Command command) {
        super(username, command);
        this.message = message;
        this.className = getClass().getSimpleName();
    }



    // --------- GETTER -------------

    /**Message Getter
     * @return the message that will be printed by the view
     */
    public String getMessage() {
        return message;
    }


    // ---------- SETTER ---------------

    /**Message Setter
     * @param message is the message that will be printed by the View
     */
    public void setMessage(String message) {
        this.message = message;
    }



}
