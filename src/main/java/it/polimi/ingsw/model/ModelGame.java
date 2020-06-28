package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.modelViewMessages.ModelUpdate;
import it.polimi.ingsw.messages.modelViewMessages.Update;
import it.polimi.ingsw.model.state.SetupState;
import it.polimi.ingsw.model.state.State;
import it.polimi.ingsw.network.server.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**ModelGame Class
 * @author Piersilvio Mancuso and Veronica Motta
 */
public class ModelGame implements Serializable {

    // -------------- FIELDS --------------

    private Board boardGame;
    private List<User> userList;
    private int currentUser;
    private State currentState;
    private List<Worker> workerList;
    private List<Cell> validCells;
    private Update updateObject;
    private Server server;

    // --------------------- CONSTRUCTOR ----------------

    /**ModelGame Constructor*/
    public ModelGame() {
        this.boardGame = new Board();
        this.userList = new ArrayList<>();
        this.currentUser = 0;
        this.currentState = new SetupState();
        this.workerList= new ArrayList<>();
        this.validCells = new ArrayList<>();
        addUpdate(new ModelUpdate(this));

    }

    /**ModelGame Copy-Constructor
     * @param modelGame is the modelGame that will be copied
     */
    public ModelGame(ModelGame modelGame){
        this.boardGame = new Board();
        for (Cell cell : modelGame.getBoard().getBuildMap()){
            this.getBoard().setCellBoard(cell);
        }
        this.validCells = new ArrayList<>(modelGame.getValidCells());
        this.userList = new ArrayList<>(modelGame.getUserList());
        this.currentState = modelGame.getCurrentState();
        this.currentUser = modelGame.getCurrentUserIndex();
        this.workerList = modelGame.getWorkerList();


    }


    // -------------- GETTER ---------------------------

    /**Board Getter
     * @return the board
     */
    public Board getBoard() {
        return boardGame;
    }


    /**Current State Getter
     * @return the current State
     */
    public State getCurrentState() {
        return currentState;
    }

    /**Server Getter
     * @return the server where the modelGame is running
     */
    public Server getServer() {
        return server;
    }


    /**UserList Getter
     * @return a list containing all the users in game
     */
    public List<User> getUserList() {
        return userList;
    }

    /**ValidCells Getter
     * @return a list of all validCells
     */
    public List<Cell> getValidCells() {
        return validCells;
    }

    /**CurrentUser Getter
     * @return the current user
     */
    public User getCurrentUser() {
        return userList.get(currentUser);
    }

    /**CurrentUserIndex Getter
     * @return the index of the current user
     */
    public int getCurrentUserIndex(){
        return currentUser;
    }

    /**WorkerList Getter
     * @return a list of all workers in game
     */
    public List<Worker> getWorkerList() {
        return workerList;
    }


    /**Get the position of a Worker
     * @param worker is the worker from who we want to know the position
     * @return the position of the worker
     */
    public Cell getWorkerPosition(Worker worker) {
        return worker.getPosition();
    }


    /**UpdateObject Getter
     * @return the Update Object
     */
    public Update getUpdateObject() {
        return updateObject;
    }



    /**BoardGame Getter
     * @return the Board of the Game
     */
    public Board getBoardGame() {
        return boardGame;
    }



    // ------------------ SETTER --------------------------

    /**Current User Setter
     * @author Veronica Motta
     * @param userIndex is the index in userList of the Current User
     */
    public void setCurrentUser(int userIndex) {
        currentUser = userIndex;
    }

    /**ValidCells Setter
     * @param validCells is a list of all valid Cells
     */
    public void setValidCells(List<Cell> validCells) {
        this.validCells = validCells;
    }

    /**Current State Setter
     * @param state is the state that will be set as current state
     */
    public void setCurrentState(State state) {
        currentState = state;
    }

    /**Server Setter
     * @param server is the server where the game is On
     */
    public void setServer(Server server) {
        this.server = server;
    }


    /**
     * set worker position after a move
     * @author Motta
     * @param worker is the worker that will be set to the position in the Board
     * @param position is position where the worker will be set
     */
    public void setWorkerPosition(Worker worker, Cell position) {
        if (worker == null) throw new NullPointerException("worker is null");
        else if (position == null) throw new NullPointerException("worker is null");
        else if (!getBoard().getBuildMap().contains(position)) throw new IndexOutOfBoundsException("Position is not in the Board");
        position = getBoard().getCell(position);
        int height = position.getHeight();
        if (height < 0 || height >= 4) throw new IllegalArgumentException("Height not valid");

        worker.setPosition(position);
    }

    /**WorkerList Setter
     * @param workerList is a list of Workers
     */
    public void setWorkerList(List<Worker> workerList) {
        this.workerList = workerList;
    }

    /**UserList Setter
     * @param userList is a List of User
     */
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setBoardGame(Board boardGame) {
        this.boardGame = boardGame;
    }

    /**UpdateObject Setter
     * @param updateObject is the update object that will be sent to clients
     */
    public void setUpdateObject(Update updateObject) {
        this.updateObject = updateObject;
    }



    // --------------- UTILITIES ---------------------

    /**Set the Update Object and, if the server exists, it will be sent to each client
     * @param update is the Update object for the View
     */
    public void addUpdate(Update update){
        if (!update.equals(updateObject) ){
            updateObject = update;
            if (server != null ) notifyServer();
        }
    }

    /**
     * add new user to play
     * @author Motta
     * @param user is the user that will be added into the game
     */
    public void addUser(User user) {
        userList.add(user);
    }

    /**
     * add worker
     * @author Motta
     * @param worker is the worker that will be added into the game
     */
    public void addWorker(Worker worker){
        workerList.add(worker);
    }

    /**
     * remove a user from a game
     * @author Motta
     * @param user is the user that will be removed from the game
     */
    public void removeUser(User user) {
        userList.remove(user);
        currentUser--;
    }

    /**
     * remove worker from a game
     * @author Motta
     * @param worker is the worker that will be removed by the game
     */
    public void removeWorker(Worker worker){
        workerList.remove(worker);
    }

    /**
     * set the next user
     * @author Motta
     */
    public void nextUser(){
        currentUser = (currentUser + 1);
        currentUser = currentUser%userList.size();
    }


    /**Create an ArrayList containing the position of all workers in game
     * @return a list of all workers' position
     */
    public List<Cell> getWorkerListPosition(){
        List<Cell> res = new ArrayList<>();

        for (Worker worker : workerList){
            res.add(worker.getPosition());
        }
        return  res;
    }

    /**Get the worker in a position, if there is one
     * @param position is the position where it could be a worker
     * @return null if there is no worker in that position, otherwise the worker in that position
     */
    public Worker getWorkerFromPosition(Cell position){
        for (Worker worker : workerList){
            if (worker.getPosition().equals(position)) return worker;
        }
        return null;
    }


    /**Get the User who has the input username
     * @param string is the username of the user looked for
     * @return the User who has the specific Username
     */
    public User getUserFromUsername(String string){
        for (User user : userList){
            if (user.getUsername().equals(string)) return user;
        }
        return null;
    }



    /**Select randomly the first User, and Create the new Board*/
    public void startGame(){
        Random random = new Random();
        setCurrentUser(Math.abs(random.nextInt()) % userList.size());
        currentState = new SetupState();
        this.validCells = getBoard().getBuildMap();
    }

    /**If the Server exists, it will send the updateObject
     */
    public void notifyServer(){
        if (server != null){
            server.broadcast(updateObject);
        }

    }

    /**Get all workers belonged to an user
     * @author Motta
     * @param user is the user from which we get his workers
     * @return a list containing all user's workers
     */
    public List<Worker> getWorkerFromUser(User user) {
        List<Worker> res = new ArrayList<>();
        for (Worker worker : workerList){
            if (worker.getUser().equals(user)) res.add(worker);
        }
        return res ;
    }

}
