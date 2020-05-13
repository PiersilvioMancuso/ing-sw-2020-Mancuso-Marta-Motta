package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.state.BuildState;
import it.polimi.ingsw.model.state.MovementState;
import it.polimi.ingsw.model.messages.controllersMessages.EndSending;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.controller.controllerState.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.messages.GodEnum;
import it.polimi.ingsw.model.messages.controllersMessages.Ack;
import it.polimi.ingsw.model.messages.controllersMessages.Nack;
import it.polimi.ingsw.model.messages.controllersMessages.Response;
import it.polimi.ingsw.model.state.EndState;
import it.polimi.ingsw.model.state.State;
import it.polimi.ingsw.view.Command;

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
    private List<GodEnum> godEnumList;
    private Worker currentWorker;
    private State lastState;
    private List<ModelColor> modelColorList;
    private int maxPlayers;
    private Response response;
    private Server server;
    private boolean gameStarted;

    // ------------- CONSTRUCTOR ----------------

    /**RemoteController Constructor
     */
    public RemoteController(Server server){
        this.playerList = new ArrayList<>();
        this.godEnumList = new ArrayList<GodEnum>(Arrays.asList(GodEnum.values()));
        this.modelColorList = new ArrayList<ModelColor>(Arrays.asList(ModelColor.values()));
        this.maxPlayers = 1;
        this.server = server;
        this.gameStarted = false;
    }



    // --------------- GETTER ---------------------

    /**Server Getter
     * @return the server to which the Remote Controller is on
     */
    public Server getServer() {
        return server;
    }


    /**MaxPlayers Getter
     * @return the number of max players that can be in game
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**GodEnumList Getter
     * @return a list of GodEnum that can be set to players as Player's God
     */
    public List<GodEnum> getGodEnumList() {
        return godEnumList;
    }

    /**ModelColorList Getter
     * @return a list of all Model Colors that can be set to each user
     */
    public List<ModelColor> getModelColorList() {
        return modelColorList;
    }


    /**Response Getter
     * @return the Response that will be sent to the current user
     */
    public Response getResponse() {
        return response;
    }


    /**LastState Getter
     * @return the last state of the game model
     */
    public State getLastState() {
        return lastState;
    }


    /**GameStarted Getter
     * @return the boolean value that says if the game is started
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**Cell Getter
     * @return the Cell
     */
    public Cell getCell() {
        return cell;
    }


    /**ModelGame Getter
     * @return the Model of the game
     */
    public ModelGame getModelGame() {
        return modelGame;
    }


    /**User List Players
     * @return a list of all players
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




    // ------------- SETTER ---------------------

    /**GodEnumList setter
     * @param godEnumList is the list of all GodEnum that can be set to each player
     */
    public void setGodEnumList(List<GodEnum> godEnumList) {
        this.godEnumList = godEnumList;
    }

    /**ModelColorList setter
     * @param modelColorList is the list of all ModelColor that can be set to each player
     */
    public void setModelColorList(List<ModelColor> modelColorList) {
        this.modelColorList = modelColorList;
    }


    /**MaxPlayers Setter
     * @param maxPlayers is the max number of players that can play
     */
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    /**Response Setter
     * @param response is the response that will be sent to Controller Client
     */
    public void setResponse(Response response) {
        this.response = response;
    }


    /**Server Setter
     * @param server is the server that will be set to Remote Controller
     */
    public void setServer(Server server) {
        this.server = server;
    }


    /**GameStarted Setter
     * @param gameStarted is the boolean value that say if the game is started
     */
    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

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

    /**PlayerList Setter
     * @param playerList is the list that will be set
     */
    public void setPlayerList(List<User> playerList) {
        this.playerList = playerList;
    }

    /**CurrentWorker Setter
     * @param currentWorker is the current Worker that will be set
     */
    public void setCurrentWorker(Worker currentWorker) {
        this.currentWorker = currentWorker;
    }

    /**LastState Setter
     * @param lastState is the last State that has been executed
     */
    public void setLastState(State lastState) {
        this.lastState = lastState;
    }



    // ------------- CONTROLLER ACTION ----------------

    /**Execute the action in input and send an Ack or a Nack containing the Data to send to the Controller Client,
     * and at the end an EndingResponse that say that it's the last Message that will be sent during this phase
     * @param action is the action that will be executed
     */
    public void executeAction(Action action){
        System.out.println(maxPlayers);


        // --------- REGISTRATION ACTION ------------
        if (action.getClassName().contains("RegistrationAction")) {
            registrationActionControl((RegistrationAction)action);
        }


            else {
                User user = getUserFromUsername(action.getUsername());

                // ------------- PlayersInGameChoice Action --------------
                if (action.getClassName().contains("PlayersInGameChoiceAction")){
                    playersInGameChoiceActionControl((PlayersInGameChoiceAction) action);
                }

                // -------------- GodInGameChoice Action -----------------
                else if (action.getClassName().contains("GodInGameChoiceAction") ){
                    godInGameChoiceActionControl((GodInGameChoiceAction)action);

                }

                // -------------- GodChoice Action --------------------
                else if (action.getClassName().contains("GodChoiceAction") ){
                    godChoiceActionControl((GodChoiceAction) action);
                }


                // -------------- COLOR CHOICE ACTION --------------------
                else if (action.getClassName().contains("ColorChoiceAction")){
                    colorChoiceActionControl((ColorChoiceAction) action);
                }

                // -------------- WorkerSetup Action -----------------
                else if (action.getClassName().contains("WorkerSetupAction") ){
                    workerSetupActionControl((WorkerSetupAction)action);

                }

                // -------------- ActivatePower Action -------------
                else if (action.getClassName().contains("ActivatePowerAction") ){
                    activationPowerControl((ActivatePowerAction) action);
                }

                // -------------- ExecuteState Action --------------
                else if (action.getClassName().contains("ExecuteControllerAction") ){
                    executionActionControl((ExecuteControllerAction) action);

                }



            }

        sendResponse();
        setResponse(new EndSending(action.getUsername(), Command.MOVE));
        sendResponse();

    }




    // ---------------- UTILITIES -----------------

    /**Get the user that has the input username
     * @param username is the username thanks which it will be returned the user
     * @return the user who has input username
     */
    public User getUserFromUsername(String username){
        for (User user : playerList){
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }


    /**Get the username of the younger user
     * @return the username of the younger user
     */
    public String getYoungerUsername(){
        int age = 1000;
        String username = "";
        for (User user : playerList){
            if (user.getAge() < age) username = user.getUsername();
        }
        return username;
    }


    /**Check if there is a User with the username in input
     * @param username is the username checked
     * @return true if there is a User with the username in input
     */
    public boolean checkUserExistenceWithUsername(String username){
        for (User user: playerList){
            if (user.getUsername().equals(username)) return true;
        }
        return false;
    }


    /**Send response to Controller Client via Virtual Client
     */
    public void sendResponse(){
        if (response != null ) server.broadcast(response);
    }


    /**Check if User has set all of his workers
     * @param user is the user checked
     * @return true if user has set two Workers
     */
    public boolean userCompleteSetup(User user){
        int count = 0;
        for (Worker worker : modelGame.getWorkerList()){
            if (worker.getUser().equals(user)) count ++;
        }

        return  count == 2;
    }


    /**Register the player on the lobby, checking if the lobby is full and if there is another player with the same username
     * It also sends Ack to the right user for game management
     * @param action is the action thanks which the registration effect is executed
     */
    public void registrationActionControl(RegistrationAction action){
        action.controlAction(this);
    }



    /**Set the maximum number of players that will play the game
     * @param action is the PlayersInGameChoiceAction thanks which max number of players in game will be set
     */
    public void playersInGameChoiceActionControl(PlayersInGameChoiceAction action){
        System.out.println(action.getNumberOfPlayers());
        if (action.getNumberOfPlayers() > 3 || action.getNumberOfPlayers() <=1){
            String message = "Invalid number of players in game";
            response = new Nack(message, action.getUsername(), Command.PLAYERS);
        }
        else {
            action.executeAction(this);
        }

    }




    /**Choose which Gods will be in the Game
     * @param action is the action that will be executed
     */
    public void godInGameChoiceActionControl(GodInGameChoiceAction action){
        action.controlAction(modelGame,this);
        sendResponse();
    }



    /**Set User's God and send a Nack if the user didn't chose a god from the list, otherwise
     * the god will be set to the correct player and if all players have set their God, the next user will receive a ColorCommand,
     * otherwise next user will receive a God Choice Command
     * @param action is the action that will trigger the godChoiceAction Execution
     */
    public void godChoiceActionControl(GodChoiceAction action){
        if (action.getGodChosen() > godEnumList.size() || action.getGodChosen() < 0){
            String errorMessage = "Incorrect God Chosen";
            response = new Nack(errorMessage, action.getUsername(),Command.GOD);
        }
        else {
            action.executeAction(godEnumList, playerList);
            modelGame.setUserList(playerList);
            modelGame.nextUser();

            User user = modelGame.getCurrentUser();

            if (user.getGod() != null){
                response = new Ack(user.getUsername(), Command.COLOR, new ColorChoiceControllerState());
            }
            else {
                response = new Ack(user.getUsername(), Command.GOD, new GodChoiceControllerState());
            }

        }
        sendResponse();
    }


    /**Set User's Color and send a Nack if the user didn't chose a color from the list, otherwise
     * the color will be set to the correct player and if all players have set their Color, the next user will receive a ColorCommand,
     * else next user will receive a Color Choice Command
     * @param action is the action that will trigger the godChoiceAction Execution
     */
    public void colorChoiceActionControl(ColorChoiceAction action){
        User user = getUserFromUsername(action.getUsername());
        if (action.getColorChosen() <0 || action.getColorChosen() >= modelColorList.size()){
            String errorMessage = "Invalid Color Chosen";
            response = new Nack(errorMessage, user.getUsername(), Command.COLOR);
        }
        else {
            action.executeAction(user, modelColorList);
            modelGame.nextUser();
            user = modelGame.getCurrentUser();

            if (user.getColor()!= null){
                modelGame.startGame();
                user = modelGame.getCurrentUser();

                response = new Ack(user.getUsername(), Command.SET_WORKER_POSITION, new WorkerSetupControllerState());
            }
            else {
                response = new Ack(user.getUsername(), Command.COLOR, new ColorChoiceControllerState());
            }
        }
        sendResponse();


    }

    /**Execute the workerSetupAction Control and Action sending a Nack if an error occurs, or an Ack with next State
     * @param action is the action that will be executed
     */
    public void workerSetupActionControl(WorkerSetupAction action){
        User user = modelGame.getUserFromUsername(action.getUsername());
        if (modelGame.getWorkerListPosition().contains(action.getCell())){
            String errorMessage = "Invalid Cell selected";
            response = new Nack(errorMessage, action.getUsername(), Command.SET_WORKER_POSITION);
            sendResponse();
        }
        else {
             action.executeAction(modelGame, user);

            if (userCompleteSetup(user)) {
                modelGame.nextUser();
                user = modelGame.getCurrentUser();

                if (userCompleteSetup(user)) {
                    response = new Ack(user.getUsername(), Command.USE_GOD_POWER, new ActivatePowerControllerState());
                }

                else response = new Ack(user.getUsername(), Command.SET_WORKER_POSITION, new WorkerSetupControllerState());

                sendResponse();
            }

            else {

                response = new Ack(action.getUsername(), Command.SET_WORKER_POSITION, new WorkerSetupControllerState());
                sendResponse();
            }

        }
    }

    /**Execute the Activation Power Control
     * @param action is the ActivatePowerAction that user sent for the execution
     */
    public void activationPowerControl(ActivatePowerAction action){
        User user = getUserFromUsername(action.getUsername());

        Cell cell = action.getCell();
        if (!modelGame.getValidCells().contains(cell)){
            String message = "Invalid Cell selected";
            response = new Nack(message, user.getUsername(),Command.USE_GOD_POWER);
        }
        else {
            Worker worker = modelGame.getWorkerFromPosition(cell);
            this.currentWorker = worker;
            action.executeAction(modelGame, worker);

            user.getGod().looseEffect(modelGame, worker);

            if (modelGame.getUserList().size() == 1){
                response = new Ack(user.getUsername(),Command.WIN, new RegisterControllerState());
            }

            else {
                if (modelGame.getCurrentState() instanceof MovementState){
                    response = new Ack(user.getUsername(), Command.MOVE, new ExecutionControllerState());
                }
                else if (modelGame.getCurrentState() instanceof BuildState){
                    response = new Ack(user.getUsername(), Command.BUILD, new ExecutionControllerState());
                }
            }

        }
        sendResponse();
    }


    /**Execute the ExecuteAction Control
     * @param action is the ExecuteControllerAction that has been sent by Controller Client
     */
    public void executionActionControl(ExecuteControllerAction action){
        Cell lastWorkerPosition = modelGame.getWorkerPosition(currentWorker);
        Cell cell = action.getCell();
        User user = getUserFromUsername(action.getUsername());


        //Invalid Cell
        if (modelGame.getValidCells().contains(cell)){
            String message = "Invalid Cell selected";

            if (modelGame.getCurrentState() instanceof BuildState){
                response = new Nack(message, action.getUsername(), Command.BUILD);
            }
            else if (modelGame.getCurrentState() instanceof MovementState){
                response = new Nack(message, action.getUsername(), Command.MOVE);
            }

        }
        //ValidCell

        else {
            action.executeAction(modelGame, currentWorker);
            user.getGod().winEffect(modelGame, currentWorker, lastWorkerPosition);

            //If winner
            if (user.getOutCome().equals(OutCome.WINNER)) {
                for (User user1 : playerList) {
                    if (!user1.equals(user)) {
                        response = new Ack(user1.getUsername(), Command.LOOSE, new RegisterControllerState());
                        sendResponse();
                    }
                }
                response = new Ack(user.getUsername(), Command.WIN, new RegisterControllerState());
            } else {
                user.getGod().looseEffect(modelGame, currentWorker);

                if (modelGame.getUserList().size() == 1) {
                    user = modelGame.getUserList().get(0);
                    for (User user1 : playerList) {
                        if (!user1.equals(user)) {
                            response = new Ack(user1.getUsername(), Command.LOOSE, new RegisterControllerState());
                            sendResponse();
                        }
                    }
                    response = new Ack(user.getUsername(), Command.WIN, new RegisterControllerState());
                } else if (modelGame.getCurrentState() instanceof EndState) {
                    modelGame.nextUser();
                    response = new Ack(modelGame.getCurrentUser().getUsername(), Command.USE_GOD_POWER, new ActivatePowerControllerState());
                } else if (!modelGame.getUserList().contains(user)) {
                    String message = "You already lose the game";
                    response = new Nack(message, user.getUsername(), Command.QUIT);
                } else {
                    if (modelGame.getCurrentState() instanceof BuildState) {
                        response = new Ack(user.getUsername(), Command.BUILD, new ExecutionControllerState());
                    } else response = new Ack(user.getUsername(), Command.MOVE, new ExecutionControllerState());
                }

            }
        }

        sendResponse();
    }



}
