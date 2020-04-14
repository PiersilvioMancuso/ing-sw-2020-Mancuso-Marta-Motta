package it.polimi.ingsw.model;

import it.polimi.ingsw.model.state.SetupState;
import it.polimi.ingsw.model.state.State;

import java.util.ArrayList;
import java.util.List;


public class ModelGame {
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
        return workerList.get(workerList.indexOf(worker)).getPosition();
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

    public Worker getWorkerFromPosition(Cell position){
        for (Worker worker : workerList){
            if (worker.getPosition().equals(position)) return worker;
        }
        return null;
    }

}
