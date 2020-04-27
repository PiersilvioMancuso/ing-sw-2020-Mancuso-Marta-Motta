package it.polimi.ingsw.controller;


import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.controller.state.ExecutionControllerState;
import it.polimi.ingsw.controller.state.GodInGameChoiceState;
import it.polimi.ingsw.controller.state.PlayersInGameChoiceState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.event.GodEnum;
import it.polimi.ingsw.model.event.response.Ack;
import it.polimi.ingsw.model.event.response.Nack;
import it.polimi.ingsw.model.event.response.Response;
import it.polimi.ingsw.model.state.EndState;
import it.polimi.ingsw.model.state.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**RemoteController Class
 * @author Piersilvio Mancuso
 */
public class RemoteController {
    private Cell cell;
    private ModelGame modelGame;
    private List<User> playerList;
    private List<GodEnum> godEnum;
    private Worker currentWorker;
    private State lastState;
    private List<ModelColor> modelColorList;
    private int maxPlayers;

    // ------------- CONSTRUCTOR ----------------

    /**RemoteController Constructor
     */
    public RemoteController(){
        this.playerList = new ArrayList<>();
        this.godEnum = new ArrayList<GodEnum>(Arrays.asList(GodEnum.values()));
        modelColorList = new ArrayList<ModelColor>(Arrays.asList(ModelColor.values()));
        this.maxPlayers = 1;
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
    public Worker getCurrentWorker() {
        return currentWorker;
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

    public User getUserFromUsername(String username){
        for (User user : playerList){
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    // ------------- SETTER ---------------------

    /**Cell Setter
     * @param cell is the cell useful for undoAction
     */
    public void setCell(Cell cell) {
        this.cell = cell;
    }

    /**ModelGame Setter
     * @param modelGame is the model of the game
     */
    public void setModelGame(ModelGame modelGame) {
        this.modelGame = modelGame;
    }

    public void setPlayerList(List<User> playerList) {
        this.playerList = playerList;
    }

    public void setCurrentWorker(Worker currentWorker) {
        this.currentWorker = currentWorker;
    }

    public void setLastState(State lastState) {
        this.lastState = lastState;
    }



    // ------------- EFFECT ----------------




    public void executeAction(Action action){

        Class instance = action.getInstance();
        Response response = new Nack();

        // --------- Registration Action ------------
        if (RegisterAction.class.equals(instance)) {
            if (!(isUserWithUsername(action.getUsername()) || playerList.size() + 1 > maxPlayers )){
                ((RegisterAction) action).executeAction(playerList);

                if (playerList.size() == 1) {
                    /*
                    TODO: Check if the player has a game not ended
                        : if yes, will be sent a message to the client asking him if he want to play another game
                        : otherwise there will be created another game
                     */


                    response = new Ack(new PlayersInGameChoiceState());
                }
                else {
                    if (playerList.size() > 1 && playerList.size() == maxPlayers){
                        response = new Ack(new GodInGameChoiceState());
                    }
                }
            }
        }
        else {
            if (!(isUserWithUsername(action.getUsername()))) response = new Nack();


            else {
                User user = getUserFromUsername(action.getUsername());

                // ------------- PlayersInGameChoice Action --------------
                if (PlayersInGameChoiceAction.class.equals(instance)){
                    ((PlayersInGameChoiceAction) action).executePower(maxPlayers);
                }


                // -------------- GodInGameChoice Action -----------------
                if (GodInGameChoiceAction.class.equals(instance)){
                    ((GodInGameChoiceAction)action).executeAction(godEnum);

                }


                // -------------- GodChoice Action --------------------
                if (GodChoiceAction.class.equals(instance)){
                    if (((GodChoiceAction)action).getGodChosen() > godEnum.size() || ((GodChoiceAction)action).getGodChosen() < 0){
                        response = new Nack();
                    }
                    else {
                        ((GodChoiceAction)action).executeAction(godEnum, playerList);
                        modelGame.setUserList(playerList);
                    }
                }


                // -------------- ColorChoice Action --------------------
                if (ColorChoiceAction.class.equals(instance)){

                    ((ColorChoiceAction)action).executeAction(user, modelColorList);
                }

                // -------------- WorkerSetup Action -----------------
                if (WorkerSetupAction.class.equals(instance)){
                    if (modelGame.getWorkerListPosition().contains(((WorkerSetupAction)action).getCell())){
                        response = new Nack();
                    }
                    else {
                        ((WorkerSetupAction)action).executeAction(modelGame, user);

                    }
                }

                // -------------- ActivatePower Action -------------
                if (ActivatePowerAction.class.equals(instance)){
                    Cell cell = ((ActivatePowerAction)action).getCell();
                    if (!modelGame.getWorkerList().contains(cell)){
                        response = new Nack();
                    }
                    else {
                        Worker worker = modelGame.getWorkerFromPosition(cell);
                        this.currentWorker = worker;
                        ((ActivatePowerAction)action).executeAction(modelGame, worker);

                        if (user.getGod().isLoser(modelGame, worker)){
                            user.setOutCome(OutCome.LOOSER);

                            int count = 0;
                            for (User player : playerList){
                                if (player.getOutCome().equals(OutCome.LOOSER)) count++;
                            }

                            if (count + 1 == maxPlayers){
                                //TODO: Esegue la vittoria dell'altro
                            }
                        }
                        else {
                            response = new Ack(new ExecutionControllerState());
                        }


                    }
                }

                // -------------- ExecuteState Action --------------
                if (ExecuteControllerAction.class.equals(instance)){
                    Cell workerPosition = modelGame.getWorkerPosition(currentWorker);
                    Cell cell = ((ExecuteControllerAction)action).getCell();
                    if (!user.getGod().getPower().getValidCells().contains(cell)){
                        response = new Nack();
                    }
                    else {
                        lastState = modelGame.getCurrentState();
                        ((ExecuteControllerAction)action).executeAction(modelGame, currentWorker);
                        if (user.getGod().getPower().isWinner(modelGame,currentWorker,workerPosition)){
                            for (User player : playerList){
                                if (player.equals(user)) player.setOutCome(OutCome.WINNER);
                                else player.setOutCome(OutCome.LOOSER);
                            }

                        }
                        else {
                            if (modelGame.getCurrentState() instanceof EndState) modelGame.getCurrentState().executeState(modelGame, currentWorker, workerPosition);
                            response = new Ack(new ExecutionControllerState());
                        }
                    }
                }


                else {
                    throw new IllegalStateException("Unexpected value: " + action.getInstance());
                }


            }
        }

    }

}
