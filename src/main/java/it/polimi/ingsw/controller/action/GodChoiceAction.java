package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.User;
import it.polimi.ingsw.model.messages.*;

import java.util.List;

/**God Choice Action
 * @author Piersilvio Mancuso
 */
public class GodChoiceAction extends Action{
    private int godChosen;


    // --------------- CONSTRUCTOR ------------------

    /**Create The GodChoiceAction Object
     * @param data is a string with the following pattern: 'username=%s;god=%s'
     */
    public GodChoiceAction(String data){
        String[] message = data.split(";");
        this.username = message[0].split("=")[1];
        this.godChosen = Integer.parseInt(message[1].split("=")[1]);
    }


    // --------------- GETTER -----------------

    /**God Getter
     * @return an Integer that represents the god chosen
     */
    public int getGodChosen() {
        return godChosen;
    }

    /**Get the object instance
     * @return the instance of the class
     */
    public Class getInstance(){
        return GodChoiceAction.class;
    }

    // ------------------ ACTION ----------------------

    /**Execute the GodChoiceAction: Set the user's God
     * @param godEnum is a list of Gods
     * @param userList is the list of all players
     */
    public void executeAction(List<GodEnum> godEnum, List<User> userList){
        for (User user: userList){
            if (user.getUsername().equals(username)) user.setGod(godEnum.get(godChosen).getGod());
        }
    }
}

