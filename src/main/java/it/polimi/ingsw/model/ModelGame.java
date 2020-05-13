package it.polimi.ingsw.model;

import it.polimi.ingsw.model.messages.modelViewMessages.ModelUpdate;
import it.polimi.ingsw.model.messages.modelViewMessages.Update;
import it.polimi.ingsw.model.state.SetupState;
import it.polimi.ingsw.model.state.State;
import it.polimi.ingsw.network.server.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
    public ModelGame(final ModelGame modelGame){
        this.boardGame = new Board();
        for (Cell cell : boardGame.getBuildMap()){
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

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
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

    public Update getUpdateObject() {
        return updateObject;
    }

    // ------------------ SETTER --------------------------

    /**Current User Setter
     * @author Motta
     * @param userIndex
     */
    public void setCurrentUser(int userIndex) {
        currentUser = userIndex;
    }

    /**ValidCells Setter
     * @param validCells is a list of all valid Cells
     */
    public void setValidCells(List<Cell> validCells) {
        this.validCells = new ArrayList<>(validCells);
    }

    /**Current State Setter
     * @param state is the state that will be set as current state
     */
    public void setCurrentState(State state) {
        currentState=state;
    }

    /**
     * set worker position after a move
     * @author Motta
     * @param worker
     * @param position
     */
    public void setWorkerPosition(Worker worker, Cell position) {
        if (worker == null) throw new NullPointerException("worker is null");
        else if (position == null) throw new NullPointerException("worker is null");
        else if (!getBoard().getBuildMap().contains(position)) throw new IndexOutOfBoundsException("Position is not in the Board");

        int height = position.getHeight();
        if (height < 0 || height >= 4) throw new IllegalArgumentException("Height not valid");

        worker.setPosition(position);
    }

    public void addUpdate(Update update){
        updateObject = update;
        notifyServer();
    }

    /**
     * add new user to play
     * @author Motta
     * @param user
     */
    public void addUser(User user) {
        userList.add(user);
    }

    /**
     * add worker
     * @author Motta
     * @param worker
     */
    public void addWorker(Worker worker){
        workerList.add(worker);
    }

    /**
     * remove a user from a game
     * @author Motta
     * @param user
     */
    public void removeUser(User user) {
        userList.remove(user);
        currentUser--;
    }

    /**
     * remove worker from a game
     * @author Motta
     * @param worker
     */
    public void removeWorker(Worker worker){
        workerList.remove(worker);
    }

    /**
     * set the next user
     * @author Motta
     */
    public void nextUser(){
        currentUser = (currentUser+1) % userList.size();
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

    public User getUserFromUsername(String string){
        for (User user : userList){
            if (user.getUsername().equals(string)) return user;
        }
        return null;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }


    public void startGame(){
        Random random = new Random();
        setCurrentUser(Math.abs(random.nextInt()) % userList.size());
        currentState = new SetupState();
    }

    public void notifyServer(){

        if (server != null){
            server.broadcast(updateObject);
        }

    }
}
