package it.polimi.ingsw.model;

import it.polimi.ingsw.model.state.MovementState;
import it.polimi.ingsw.model.state.SetupState;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ModelGameTest {

    ModelGame modelGame;

    @Before
    public void setUp() throws Exception {
        modelGame = new ModelGame();
    }

    @Test
    public void getBoard_initializedBoard_shouldReturnABoard() {
        Board board = new Board();
        assertEquals(board, modelGame.getBoard());
    }

    @Test
    public void getCurrentState_onCreation_shouldReturnSetupState() {
        assertTrue(modelGame.getCurrentState() instanceof SetupState);
    }

    @Test
    public void getUserList_afterThreeUsersInsert_shouldReturnAListOfUserContainingTheInsertedUsers() {
        User user = new User("Piersilvio");
        User user1 = new User("Mattia");
        User user2 = new User("Veronica");

        modelGame.addUser(user);
        modelGame.addUser(user1);
        modelGame.addUser(user2);

        List<User> userList= new ArrayList<>();
        userList.add(user);
        userList.add(user1);
        userList.add(user2);

        assertTrue(modelGame.getUserList().containsAll(userList));
        assertTrue(userList.containsAll(modelGame.getUserList()));
        assertTrue(modelGame.getUserList().size() == 3);

    }

    @Test
    public void getCurrentUser_afterThreeUsersInsert_shouldReturnTheCurrentUser() {
        User user = new User("Piersilvio");
        User user1 = new User("Mattia");
        User user2 = new User("Veronica");

        modelGame.addUser(user);
        modelGame.addUser(user1);
        modelGame.addUser(user2);

        assertEquals(user, modelGame.getCurrentUser());

    }

    @Test
    public void getWorkerList_afterTwoWorkerInsert_shouldReturnAListOfWorker() {
        User user = new User("Piersilvio");
        Worker worker = new Worker(user);
        Worker worker1 = new Worker(user);

        modelGame.addWorker(worker);
        modelGame.addWorker(worker1);

        List<Worker> workerList = new ArrayList<>();
        workerList.add(worker);
        workerList.add(worker1);

        assertTrue(workerList.containsAll(modelGame.getWorkerList()));
        assertTrue(modelGame.getWorkerList().containsAll(workerList));
    }

    @Test
    public void getWorkerListPosition_afterTwoWorkerInsert_shouldReturnAListOfWorkersPositions() {
        User user = new User("Piersilvio");
        Worker worker = new Worker(user);
        Worker worker1 = new Worker(user);

        Cell cell = new Cell(0,0);
        Cell cell1 = new Cell(1,1);

        modelGame.addWorker(worker);
        modelGame.addWorker(worker1);

        List<Cell> workerListPosition = new ArrayList<>();
        workerListPosition.add(cell);
        workerListPosition.add(cell1);

        modelGame.setWorkerPosition(worker, cell);
        modelGame.setWorkerPosition(worker1, cell1);

        assertTrue(modelGame.getWorkerListPosition().containsAll(workerListPosition));
        assertTrue(modelGame.getWorkerListPosition().equals(workerListPosition));
    }

    @Test
    public void getWorkerPosition_afterTwoWorkerInsert_shouldReturnThePositionOfEachWorker() {
        User user = new User("Piersilvio");
        Worker worker = new Worker(user);
        Worker worker1 = new Worker(user);

        Cell cell = new Cell(0,0);
        Cell cell1 = new Cell(1,1);

        modelGame.addWorker(worker);
        modelGame.addWorker(worker1);


        modelGame.setWorkerPosition(worker, cell);
        modelGame.setWorkerPosition(worker1, cell1);

        assertEquals(cell, modelGame.getWorkerPosition(worker));
        assertEquals(cell1, modelGame.getWorkerPosition(worker1));
    }

    @Test
    public void getWorkerFromUser_PiersilvioUser_shouldReturnAListOfAllWorkersOfPiersilvio() {
        User user = new User("Piersilvio");
        Worker worker = new Worker(user);
        Worker worker1 = new Worker(user);

        modelGame.addWorker(worker);
        modelGame.addWorker(worker1);

        List<Worker> workerList= new ArrayList<>();
        workerList.add(worker);
        workerList.add(worker1);

        assertEquals(workerList, modelGame.getWorkerFromUser(user));
    }

    @Test
    public void setCurrentUser_IntegerTwo_shouldSetCurrentUserToTwo() {
        User user = new User("Piersilvio");
        User user1 = new User("Mattia");
        User user2 = new User("Veronica");

        modelGame.addUser(user);
        modelGame.addUser(user1);
        modelGame.addUser(user2);

        modelGame.setCurrentUser(2);
        assertEquals(user2, modelGame.getCurrentUser());
    }

    @Test
    public void setCurrentState_MovementState_shouldSetCurrentStateToMovementState() {
        modelGame.setCurrentState(new MovementState());
        assertTrue(modelGame.getCurrentState() instanceof MovementState);
    }

    @Test
    public void setWorkerPosition_workerAndLeftUpsideCornerCell_shouldSetWorkerPositionToLeftUpsideCornerCell() {
        User user = new User("Piersilvio");
        Worker worker = new Worker(user);
        Cell cell = new Cell(0,0);
        modelGame.addWorker(worker);

        modelGame.setWorkerPosition(worker, cell);

        assertEquals(cell, modelGame.getWorkerPosition(worker));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setWorkerPosition_workerAndADomeCell_shouldThrowIllegalArgumentException() throws IllegalArgumentException{
        User user = new User("Piersilvio");
        Worker worker = new Worker(user);
        Cell cell = new Cell(0,0,4);

        modelGame.setWorkerPosition(worker,cell);
    }

    @Test(expected = NullPointerException.class)
    public void setWorkerPosition_nullParameters_shouldThrowNullPointerException() throws IllegalArgumentException{
        User user = new User("Piersilvio");
        Worker worker = new Worker(user);
        Cell cell = null;

        modelGame.setWorkerPosition(worker,cell);
    }



    @Test
    public void addUser_PiersilvioUser_shouldInsertPiersilvioUserIntoTheGame() {
        User user = new User("Piersilvio");
        modelGame.addUser(user);
        assertTrue(modelGame.getUserList().contains(user));
    }

    @Test
    public void addWorker_PiersilvioUserWorker_shouldInsertPiersilvioUserWorkIntoTheGame() {
        User user = new User("Piersilvio");
        Worker worker = new Worker(user);
        modelGame.addWorker(worker);

        assertTrue(modelGame.getWorkerList().contains(worker));
    }

    @Test
    public void removeUser_PiersilvioUser_shouldRemovePiersilvioUserFromGame() {
        User user = new User("Piersilvio");
        modelGame.addUser(user);

        modelGame.removeUser(user);
        assertTrue(!modelGame.getUserList().contains(user));

    }

    @Test
    public void removeWorker_PiersilvioUserWorker_shouldRemovePiersilvioUserWorkerFromGame() {
        User user = new User("Piersilvio");
        Worker worker = new Worker(user);
        modelGame.addWorker(worker);

        modelGame.removeWorker(worker);
        assertTrue(!modelGame.getWorkerList().contains(worker));
    }

    @Test
    public void nextUser_fromPiersilvio_shouldSetCurrentUserAsMattia() {
        User user = new User("Piersilvio");
        User user1 = new User("Mattia");
        User user2 = new User("Veronica");

        modelGame.addUser(user);
        modelGame.addUser(user1);
        modelGame.addUser(user2);

        modelGame.nextUser();
        assertTrue(user1.equals(modelGame.getCurrentUser()));
    }


    @Test
    public void getWorkerFromCell_cellWithAWorkerInside_shouldReturnTheWorkerInside(){
        User user = new User("Piersilvio");
        Worker worker = new Worker(user);

        modelGame.addWorker(worker);
        Cell cell = new Cell(2,2);
        worker.setPosition(cell);


        assertTrue(modelGame.getWorkerFromPosition(cell).equals(worker));
    }

    @Test
    public void getWorkerFromCell_cellWithoutAWorkerInside_shouldReturnNull(){
        User user = new User("Piersilvio");
        Worker worker = new Worker(user);

        modelGame.addWorker(worker);
        Cell cell = new Cell(2,2);
        worker.setPosition(cell);



        assertTrue(modelGame.getWorkerFromPosition(new Cell(2,1)) == null);
    }



}