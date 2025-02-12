package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.controllerState.GodChoiceControllerState;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.messages.GodEnum;
import it.polimi.ingsw.messages.controllersMessages.Ack;
import it.polimi.ingsw.messages.controllersMessages.Nack;
import it.polimi.ingsw.messages.modelViewMessages.GodListUpdate;
import it.polimi.ingsw.view.Command;

import java.util.ArrayList;
import java.util.List;

/**GodInGameChooseAction
 * @author Piersilvio Mancuso
 */
public class GodInGameChoiceAction extends Action{
    private final List<Integer> godList;


    // -------------- CONSTRUCTOR ----------------

    /**GodInGameChooseAction Constructor
     * @param data is a string that will be parsed taking the god's index
     */
    public GodInGameChoiceAction(String data){
        super();
        this.className = "GodInGameChoiceAction";
        godList = new ArrayList<>();
        String[] godArray = data.split(";");
        this.username = godArray[0].split("=")[1];
        for (int i = 1; i < godArray.length; i++){
            godList.add(Integer.parseInt(godArray[i].split("=")[1]) );
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
    /**Set the list of Gods
     * @param gods is a list of GodEnum
     * @return a list containing just the gods selected
     */
    public List<GodEnum> executeAction(List<GodEnum> gods){
        List<GodEnum> copy = new ArrayList<>();

        for (int i = 0; i < gods.size(); i++){
            if (godList.contains(i)) copy.add(gods.get(i));
        }
        return copy;
    }


    // --------------- CONTROLLER ACTION --------------

    /**Set the Gods that players in game can choose as their god
     * @param remoteController is the remoteController that will execute the action
     */
    public void controlAction(RemoteController remoteController){
        ModelGame modelGame = remoteController.getModelGame();
        boolean checkIntegrity = true;
        List<GodEnum> godEnumList = remoteController.getGodEnumList();

        // -------- Check if there is any repetition
        for (int i = 0; i < godList.size() - 1; i++){
            for (int j = i + 1; j < godList.size(); j++){
                if (godList.get(j).equals(godList.get(i))) {
                    checkIntegrity = false;
                    break;
                }
            }
            if (!checkIntegrity) break;
        }

        // ------------- Check if there is any value that's not in godEnumList range
        for (int godIndex : godList){
            if (godIndex < 0 || godIndex >= godEnumList.size()) {
                checkIntegrity = false;
                break;
            }
        }

        // ------------ If there is any problem send a Nack thanks which the action will be repeated
        if (!checkIntegrity){
            String message = "Invalid God Chosen";

            if (godList.size() == 3){
                remoteController.setResponse(new Nack(message, username, Command.GOD_LIST_THREE));

            }
            else {
                remoteController.setResponse(new Nack(message, username, Command.GOD_LIST_TWO)) ;

            }

        }

        // ----------- If there is no problem the action will be executed and it will be sent an Ack for Color Choice
        else {

            remoteController.setGodEnumList(executeAction(godEnumList));
            modelGame.setCurrentUser(remoteController.getPlayerList().indexOf(remoteController.getUserFromUsername(username)));
            modelGame.nextUser();



            remoteController.getModelGame().addUpdate(new GodListUpdate(remoteController.getGodEnumList()));


            User user = modelGame.getCurrentUser();

            remoteController.setResponse(new Ack(user.getUsername(), Command.GOD, new GodChoiceControllerState()));



        }
    }

}
