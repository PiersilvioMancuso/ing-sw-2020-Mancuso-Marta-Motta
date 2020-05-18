package it.polimi.ingsw.messages.controllersMessages;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.view.Command;

/**Abstract Response Class
 * @author Piersilvio Mancuso
 */
abstract public class Response extends Message {


    protected String username;
    protected Command command;


    // ----------- CONSTRUCTOR ---------

    /**Response Constructor
     * @param username is the username to which the Response can be used
     * @param command is the command that Controller Client will set to the View
     */
    public Response(String username, Command command) {
        this.username = username;
        this.command = command;
        this.className = super.getClassName() + "-Response";
    }


    // ------------- GETTER ---------------

    /**Username Getter
     * @return the username to which the message is related
     */
    public String getUsername() {
        return username;
    }


    /**Command Getter
     * @return the Command that will be set to the View
     */
    public Command getCommand() {
        return command;
    }


    /**ClassNameGetter
     * @return the name of the class
     */
    public String getClassName() {
        return className;
    }

    // ------------- SETTER --------------------

    /**Username Setter
     * @param username is the username to which the message is valid
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**Command Setter
     * @param command is the Command that will be set to the View
     */
    public void setCommand(Command command) {
        this.command = command;
    }


}
