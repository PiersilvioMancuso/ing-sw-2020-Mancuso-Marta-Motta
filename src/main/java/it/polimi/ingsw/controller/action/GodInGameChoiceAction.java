package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.messages.GodEnum;

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

    /**Get the object instance
     * @return the instance of the class
     */
    public Class getInstance(){
        return GodInGameChoiceAction.class;
    }




    // ---------------- ACTION ----------------
    /**Execute the Action of GodInGameChoose
     */
    public void executeAction(List<GodEnum> gods){
        List<GodEnum> copy = new ArrayList<>();

        for (int i = 0; i < gods.size(); i++){
            if (godList.contains(i)) copy.add(gods.get(i));
        }
        gods = new ArrayList<>(copy);
    }

}
