package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.RemoteController;

import java.io.Serializable;

/**Action Abstract Class
 * @author Piersilvio Mancuso
 */
abstract public class Action implements Serializable {

    protected String className;
    protected String username;



    // ---------- CONSTRUCTOR --------------

    /**Action Constructor
     */
    protected Action() {
        this.className = getClass().getSimpleName();
    }


    // ------------ GETTER -------------------

    /**Username Getter
     * @return the username of the user that generates the action
     */
    public String getUsername() {
        return username;
    }

    /**ClassName Getter
     * @return the name of the class
     */
    public String getClassName() {
        return className;
    }



    // ----------------- CONTROLLER ACTION -----------------

    /**Execute the ActionControl
     * @param remoteController is the remoteController that will execute the action
     */
    abstract public void controlAction(RemoteController remoteController);
}
