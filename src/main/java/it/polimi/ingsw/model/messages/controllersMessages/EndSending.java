package it.polimi.ingsw.model.messages.controllersMessages;

import it.polimi.ingsw.model.messages.controllersMessages.Response;
import it.polimi.ingsw.view.Command;

/**EndSending Class
 */
public class EndSending extends Response {

    /**
     * EndSending Constructor
     *
     * @param username is the username to which the Response can be used
     * @param command  is the command that Controller Client will set to the View
     */
    public EndSending(String username, Command command) {
        super(username, command);
        this.className = "EndSending";
    }
}
