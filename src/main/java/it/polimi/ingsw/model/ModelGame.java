package it.polimi.ingsw.model;

import it.polimi.ingsw.model.state.SetupState;
import it.polimi.ingsw.model.state.State;

import java.util.ArrayList;
import java.util.List;


public class ModelGame implements Cloneable{
    private Board boardGame;
    private List<User> userList;
    private int currentUser;
    private State currentState;
    private List<Worker> workerList;

    public ModelGame() {
        this.boardGame = new Board();
        this.userList = new ArrayList<>();
        this.currentUser = 0;
        this.currentState = new SetupState();
        this.workerList= new ArrayList<>();
    }

    public ModelGame(final ModelGame modelGame){
        this.boardGame = new Board();
        for (Cell cell : boardGame.getBuildMap()){
            this.getBoard().setCellBoard(cell);
        }
        this.userList = new ArrayList<>(modelGame.getUserList());
        this.currentState = modelGame.getCurrentState();
        this.currentUser = modelGame.getIntegerCurrentUser();
        this.workerList = modelGame.getWorkerList();
    }



    public Board getBoard() {
        return boardGame;
    }

    public State getCurrentState() {
        return currentState;
    }

    public List<User> getUserList() {
        return userList;
    }

    public User getCurrentUser() {
        return userList.get(currentUser);
    }

    public int getIntegerCurrentUser(){
        return currentUser;
    }

    public List<Worker> getWorkerList() {
        return workerList;
    }


    /**
     * worker's position on the board
     * @author Motta
     * @param worker
     * @return workerListPosition in a specific index
     */
    public Cell getWorkerPosition(Worker worker) {
        return worker.getPosition();
    }

    /**
     * find User's workers
     * @author Motta
     * @param user
     * @return user's workers
     */
    public List<Worker> getWorkerFromUser(User user) {
        List<Worker> res = new ArrayList<>();
        for (Worker worker : workerList){
            if (worker.getUser().equals(user)) res.add(worker);
        }
        return res ;
    }


    /**
     * set current user
     * @author Motta
     * @param userIndex
     */
    public void setCurrentUser(int userIndex) {
        currentUser = userIndex;
    }

    /**
     * set current state
     * @author Motta
     * @param state
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
}
