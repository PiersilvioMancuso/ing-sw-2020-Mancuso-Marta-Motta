package it.polimi.ingsw.controller;


import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.event.GodList;
import it.polimi.ingsw.model.event.response.Ack;
import it.polimi.ingsw.model.event.response.Nack;
import it.polimi.ingsw.model.event.response.Response;
import it.polimi.ingsw.model.state.State;

import java.util.ArrayList;
import java.util.List;


/**RemoteController Class
 * @author Piersilvio Mancuso
 */
public class RemoteController {
    private Cell cell;
    private ModelGame modelGame;
    private List<User> playerList;
    private GodList godList;
    private Worker worker;
    private State lastState;

    // ------------- CONSTRUCTOR ----------------

    /**RemoteController Constructor
     * @param modelGame is the model of the game
     */
    public RemoteController(ModelGame modelGame){
        this.modelGame = modelGame;
        this.playerList = new ArrayList<>();
        this.godList = new GodList();
    }



    // --------------- GETTER ---------------------

    /**Cell Getter
     * @return the Cell
     */
    public Cell getCell() {
        return cell;
    }


    /**ModelGame Getter
     * @return the ModelGame
     */
    public ModelGame getModelGame() {
        return modelGame;
    }


    /**User List Players
     * @return a list of players
     */
    public List<User> getPlayerList() {
        return playerList;
    }


    /**Current Worker Getter
     * @return the Current Worker
     */
    public Worker getWorker() {
        return worker;
    }


    /**Check if there is a User with the username in input
     * @param username is the username checked
     * @return true if there is a User with the username in input
     */
    public boolean isUserWithUsername(String username){
        for (User user: playerList){
            if (user.getUsername().equals(username)) return true;
        }
        return false;
    }


    // ------------- SETTER ---------------------

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setModelGame(ModelGame modelGame) {
        this.modelGame = modelGame;
    }

    public void setPlayerList(List<User> playerList) {
        this.playerList = playerList;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public void setLastState(State lastState) {
        this.lastState = lastState;
    }



    // ------------- EFFECT ----------------




    public void executeAction(Action action){
        try {
            if (action instanceof RegisterAction){
                if (isUserWithUsername(action.getUsername())) throw new IllegalArgumentException("There is an other User with the same username");
                ((RegisterAction) action).executeAction(modelGame);
            }
        }
        catch (Exception e){
            ;
        }




    }

}
