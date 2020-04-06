package it.polimi.ingsw.model;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.List;


public class ModelGame {
    private Board boardGame;
    private List<User> userList;
    private int currentUser;
    private State currentState;
    private List<Worker> workerList;
    private List<int[]> workerListPosition;

    public ModelGame() {
        this.boardGame = new Board();
        this.userList = new ArrayList<User>();
        this.currentUser = 0;
        this.workerList= new ArrayList<Worker>();
        this.workerListPosition = new ArrayList<int[]>();
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

    public List<int[]> getWorkerListPosition() {
        return workerListPosition;
    }

    /**
     * worker's position on the board
     * @author Motta
     * @param worker
     * @return workerListPosition in a specific index
     */
    public int[] getWorkerPosition(Worker worker) {
        return workerListPosition.get(workerList.indexOf(worker));
    }

    /**
     * find User's workers
     * @author Motta
     * @param user
     * @return user's workers
     */
    public List<Worker> getWorkerFromUser(User user) {
        int index = userList.indexOf(user);
        List<Worker> res = new ArrayList<Worker>();
        res.add(workerList.get(index*2));
        res.add(workerList.get(index*2+1));
        return res ;
    }

    /**
     * find Worker's user
     * @author Motta
     * @param worker
     * @return worker's user
     */
    public User getUserFromWorker(Worker worker){
        int index = workerList.indexOf(worker);
        return userList.get(index/2);
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
    public void setWorkerPosition(Worker worker, int[] position) {
        if (position[0]<0 || position[0]>4 || position[1]<0 || position[1]>4) throw new ArrayIndexOutOfBoundsException("position is not in the Board");
        int height = getBoard().getBuildHeight(position);
        if (height<0 || height>4) throw new IllegalArgumentException("height not valid");
        if (worker == null) throw new IllegalArgumentException("worker is null");
        int index = workerList.indexOf(worker);
        workerListPosition.set(index, position);
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
     * @author motta
     * @param user
     */
    public void addWorker(User user){
        workerList.add(new Worker(user));
        workerListPosition.add(new int[2]);
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
     * @author motta
     * @param worker
     */
    public void removeWorker(Worker worker){
        int index = workerList.indexOf(worker);
        workerList.remove(index);
        workerListPosition.remove(index);
    }

    /**
     * set the next user
     * @author motta
     * @param user
     */
    public void nextUser(User user){
        currentUser = (currentUser+1) % userList.size();
    }
}
