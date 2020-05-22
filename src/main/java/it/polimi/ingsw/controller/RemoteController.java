package it.polimi.ingsw.controller;


import it.polimi.ingsw.messages.controllersMessages.EndSending;
import it.polimi.ingsw.controller.data.XMLLoader;
import it.polimi.ingsw.controller.data.XMLParser;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.controller.action.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.messages.GodEnum;
import it.polimi.ingsw.messages.controllersMessages.Response;
import it.polimi.ingsw.model.state.State;
import it.polimi.ingsw.view.Command;

import java.io.File;
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
    private boolean gameEnded = false;
    private static final String ENDNAME = "ENDNAME";
    private final File file = new File(  "src/main/java/it/polimi/ingsw/controller/data/model.xml");
    private ModelGame modelCopy;

    // ------------- CONSTRUCTOR ----------------

    /**RemoteController Constructor
     * @param server is the server
     */
    public RemoteController(Server server){
        this.playerList = new ArrayList<>();
        this.godEnumList = new ArrayList<>(Arrays.asList(GodEnum.values()));
        this.modelColorList = new ArrayList<>(Arrays.asList(ModelColor.values()));
        this.maxPlayers = 1;
        this.server = server;
        this.gameStarted = false;
        this.modelCopy = new ModelGame(XMLLoader.modelGameCreator(file)) ;
    }





    // --------------- GETTER ---------------------

    /**ModelCopy Getter
     * @return a copy of the ModelGame
     */
    public ModelGame getModelCopy() {
        return modelCopy;
    }

    /**ENDNAME Getter
     * @return the name that can't be used for playing
     */
    public static String getENDNAME() {
        return ENDNAME;
    }

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
        return this.gameStarted;
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

    /**GameEnded Getter
     * @return a boolean value that says if the game is ended
     */
    public boolean isGameEnded() {
        return gameEnded;
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

    /**GameEnded Setter
     * @param gameEnded is the boolean value that denote the end of a game
     */
    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
        if (gameEnded) resetData();
    }


    /**ModelCopy Setter
     * @param modelCopy is the modelCopy that will be set to the Remote Controller for the Undo Action and for the Saving Data
     */
    public void setModelCopy(ModelGame modelCopy) {
        this.modelCopy = modelCopy;
    }

    // ------------- CONTROLLER ACTION ----------------

    /**Execute the action in input and send an Ack or a Nack containing the Data to send to the Controller Client,
     * and at the end an EndingResponse that say that it's the last Message that will be sent during this phase
     * @param action is the action that will be executed
     */
    public void executeAction(Action action){

        action.controlAction(this);

        sendResponse();
        setResponse(new EndSending(action.getUsername(), Command.MOVE));
        sendResponse();

    }




    // ---------------- UTILITIES -----------------

    /**Save the modelCopy into the model.xml file
     */
    public void saveData(){
        XMLParser.saveModel(modelCopy, file);
    }

    /**Reset the model.xml file
     */
    public void resetData(){
        modelCopy = new ModelGame();
        saveData();
    }

    /**Check if the users that are registered into the lobby and the users on the Save Data are the same
     * @return a boolean that says if the users registered and the users on the Save Data are the same
     */
    public boolean checkUsersInCopy(){
        if (modelCopy.getUserList().size() == 0) return false;
        if (modelCopy.getUserList().size() != playerList.size()) return false;
        for (User user : modelCopy.getUserList()){
            String username = user.getUsername();
            int age = user.getAge();

            if (getUserFromUsername(username) == null || getUserFromUsername(username).getAge() != age) return false;
        }
        return true;
    }

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


    /**Get the younger user
     * @return the younger user
     */
    public User getYoungestUser(){
        int age = 1000;
        User younger = null;
        for (User user : playerList){
            if (user.getAge() < age) {
                younger = user;
                age = younger.getAge();
            }
        }
        return younger;
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

}
