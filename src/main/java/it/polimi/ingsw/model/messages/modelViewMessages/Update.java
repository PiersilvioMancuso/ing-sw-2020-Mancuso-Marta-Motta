package it.polimi.ingsw.model.messages.modelViewMessages;

import it.polimi.ingsw.model.messages.Message;
import it.polimi.ingsw.view.View;

/**Update abstract Class
 * @author Piersilvio Mancuso
 */
abstract public class Update extends Message {

    /**Update Constructor
     */
    public Update(){
        this.className = super.getClassName() + "-Update";
    }

    /**Update the View
     * @param view is the view to which changes can be executed
     */
    abstract public void setChanges(View view);
}
