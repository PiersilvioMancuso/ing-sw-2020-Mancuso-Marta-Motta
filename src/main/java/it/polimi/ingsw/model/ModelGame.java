package it.polimi.ingsw.model;

import java.util.List;


public class ModelGame {
    private Board boardGame;
    private List<User> userList;
    private int currentUser = 0;
    private List<Worker> workerList;
    private List<int[]> workerListPosition;

    public ModelGame() {
    }

    public Board getBoard() {
        return null;
    }

    public List<User> getUserList() {
        return null;
    }

    public User getCurrentUser() {
        return null;
    }

    public List<Worker> getWorkerList() {
        return null;
    }

    public List<int[]> getWorkerListPosition() {
        return null;
    }

    public int[] getWorkerPosition(Worker worker) {
        return null;
    }

    public List<Worker> getUserWorkerList(User user) {
        return null;
    }

    public void setCurrentUser(int userIndex) {
    }

    public void setWorkerPosition(Worker worker, int[] position) {
    }


    public void deleteUser(User user) {
    }


}
