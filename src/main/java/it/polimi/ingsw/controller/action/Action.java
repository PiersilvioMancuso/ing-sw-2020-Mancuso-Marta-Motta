package it.polimi.ingsw.controller.action;

/**Abstract Class Action
 * @author Piersilvio Mancuso
 */
abstract public class Action {

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
}
