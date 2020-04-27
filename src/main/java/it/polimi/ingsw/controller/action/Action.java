package it.polimi.ingsw.controller.action;

/**Abstract Class Action
 * @author Piersilvio Mancuso
 */
abstract public class Action {
    protected String username;


    /**Username Getter
     * @return the username of the user that generates the action
     */
    public String getUsername() {
        return username;
    }


    /**Get the object instance
     * @return the instance of the class
     */
    public Class getInstance(){
        return Action.class;
    }

}
