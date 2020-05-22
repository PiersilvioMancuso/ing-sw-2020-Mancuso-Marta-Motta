package it.polimi.ingsw.messages.modelViewMessages;

import it.polimi.ingsw.messages.Message;
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


    /**ClassName Getter
     * @return the name of the Class
     */
    @Override
    public String getClassName() {
        return super.getClassName();
    }


    /**Update the View
     * @param view is the view to which changes can be executed
     */
    abstract public void setChanges(View view);
}
