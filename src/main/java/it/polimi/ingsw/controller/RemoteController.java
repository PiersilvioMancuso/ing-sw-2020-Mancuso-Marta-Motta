package it.polimi.ingsw.controller;


import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.controller.state.GodInGameChoiceState;
import it.polimi.ingsw.controller.state.PlayersInGameChoiceState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.messages.GodEnum;
import it.polimi.ingsw.model.messages.controllersMessages.Ack;
import it.polimi.ingsw.model.messages.controllersMessages.Nack;
import it.polimi.ingsw.model.messages.controllersMessages.Response;
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


    /**Execute the action in input and send an Ack or a Nack containing the Data to send to the Controller Client
     * @param action is the action that will be executed
     */
    public void executeAction(Action action){

        Class instance = action.getInstance();
        //Response response = new Nack();

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


                    //response = new Ack(new PlayersInGameChoiceState());
                }
                else {
                    if (playerList.size() > 1 && playerList.size() == maxPlayers){
                      //  response = new Ack(new GodInGameChoiceState());
                    }
                }
            }
        }
        else {
            //if (!(isUserWithUsername(action.getUsername()))) response = new Nack();


            //else {
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
              //          response = new Nack();
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
                //        response = new Nack();
                    }
                    else {
                        ((WorkerSetupAction)action).executeAction(modelGame, user);

                    }
                }

                // -------------- ActivatePower Action -------------
                if (ActivatePowerAction.class.equals(instance)){
                    Cell cell = ((ActivatePowerAction)action).getCell();
                    if (!modelGame.getWorkerList().contains(cell)){
                  //      response = new Nack();
                    }
                    else {
                        Worker worker = modelGame.getWorkerFromPosition(cell);
                        this.currentWorker = worker;
                        ((ActivatePowerAction)action).executeAction(modelGame, worker);

                        user.getGod().looseEffect(modelGame, worker);

                        if (modelGame.getUserList().size() == 1){
                            //End Game
                        }

                        else if (!modelGame.getUserList().contains(user)){
                            //Send Nack Loose
                        }
                        else {
                            //Send Ack
                        }

                    }
                }

                // -------------- ExecuteState Action --------------
                if (ExecuteControllerAction.class.equals(instance)){
                    Cell lastWorkerPosition = modelGame.getWorkerPosition(currentWorker);
                    Cell cell = ((ExecuteControllerAction)action).getCell();
                    if (!user.getGod().getPower().getValidCells().contains(cell)){
                    //    response = new Nack();
                    }
                    else {
                        lastState = modelGame.getCurrentState();
                        ((ExecuteControllerAction)action).executeAction(modelGame, currentWorker);
                        user.getGod().winEffect(modelGame, currentWorker, lastWorkerPosition);

                        if (user.getOutCome().equals(OutCome.WINNER)){
                            //send Ack
                        }
                        else if (!(modelGame.getCurrentState() instanceof EndState)){
                            user.getGod().looseEffect(modelGame, currentWorker);

                            if (modelGame.getUserList().size() == 1){
                                //End Game
                            }

                            else if (!modelGame.getUserList().contains(user)){
                                //Send Nack Loose
                            }
                            else {
                                //Send Ack
                            }

                        }

                        else {
                            //send Ack
                        }
                    }
                }



            //}
        }

    }

    // -------------- UTILITIES --------------


}
