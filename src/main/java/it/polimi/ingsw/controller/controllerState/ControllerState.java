package it.polimi.ingsw.controller.controllerState;

import it.polimi.ingsw.controller.action.Action;

/**Abstract Controller State Class
 * @author Piersilvio Mancuso
 */
abstract public class ControllerState {

    protected String className;


    // ---------- CONSTRUCTOR -------------

    /**ControllerState Constructor
     */
    public ControllerState() {
        this.className = getClass().getSimpleName();
    }

    // ------------- GETTER -----------

    /**ClassName Getter
     * @return the name of the class
     */
    public String getClassName() {
        return className;
    }

    /**Abstract Create Action
     * @param string is the string with which an Action will be created
     * @return the Action
     */
    abstract public Action createAction(String string);


}
