package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.event.response.Ack;
import it.polimi.ingsw.model.event.response.Nack;
import it.polimi.ingsw.model.event.response.Response;

import java.util.ArrayList;
import java.util.List;

/**GodInGameChooseAction
 * @author Piersilvio Mancuso
 */
public class GodInGameChoiceAction extends Action{
    private List<Integer> godList;


    // -------------- CONSTRUCTOR ----------------

    /**GodInGameChooseAction Constructor
     * @param data is a string in the following pattern: god=n1god=n2..
     */
    public GodInGameChoiceAction(String data){
        godList = new ArrayList<>();
        String[] godArray = data.split(";");
        this.username = godArray[0].split("=")[1];
        for (int i = 1; i < godArray.length; i++){
            godList.add(Integer.parseInt(godArray[i].split("=")[1]));
        }
    }

    // -------------- GETTER ------------------

    /**Get God List In Game
     * @return a list of Integer who denotes the list of gods in Game
     */
    public List<Integer> getGodList() {
        return godList;
    }


    // ---------------- ACTION ----------------
    /**Execute the Action of GodInGameChoose
     * @return an Ack if the godList is a valid List, otherwise a Nack
     */
    public void executeAction(){
        for (int i : godList) if (i<0 || i>8) throw new IllegalArgumentException("Invalid God Chosen");
    }

}
