package it.polimi.ingsw.controller.action;

/**Undo Action
 * @author Piersilvio Mancuso
 */
public class UndoAction extends Action{

    /**Undo Action Constructor
     * @param data is the string with which the action already made will be canceled
     */
    public UndoAction(String data){
        String[] message = username.split(";");
        this.username = message[0].split("=")[1];
    }

    /**Get the object instance
     * @return the instance of the class
     */
    public Class getInstance(){
        return UndoAction.class;
    }

}
