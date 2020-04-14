package it.polimi.ingsw.model;

import it.polimi.ingsw.model.state.MovementState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovementStateTest {

    MovementState state;
    ModelGame modelGame;
    Worker worker;
    Cell cell;

    @Before
    public void setUp() throws Exception {
        state = new MovementState();
        modelGame = new ModelGame();
        worker = new Worker(new User("Test"));
        cell = new Cell();
    }

    @Test(expected = NullPointerException.class)
    public void executeState_nullArgument_shouldThrowsNullPointerException() {
        state.executeState(modelGame,null,cell);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeState_occupiedCell_shouldThrowIllegalArgumentException(){
        modelGame.addWorker(worker);
        modelGame.setWorkerPosition(worker, cell);

        Worker testWorker = new Worker(new User("testUser"));
        modelGame.addWorker(testWorker);
        state.executeState(modelGame, testWorker, cell);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeState_domeCell_shouldThrowIllegalArgumentException(){
        modelGame.addWorker(worker);
        cell.setHeight(4);
        state.executeState(modelGame, worker, cell);
    }

    @Test
    public void executeState_unoccupiedCell_shouldSetWorkerPositionEqualsToTheCell(){
        modelGame.addWorker(worker);
        state.executeState(modelGame, worker, cell);
        assertSame(cell, modelGame.getWorkerPosition(worker));
    }
}