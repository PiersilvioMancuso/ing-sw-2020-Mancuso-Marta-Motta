package it.polimi.ingsw.model.state;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.state.SetupState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SetupStateTest {
    SetupState state;
    ModelGame modelGame;
    Worker worker;
    Cell cell;

    @Before
    public void setUp() throws Exception {
        state = new SetupState();
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

    @Test
    public void executeState_unoccupiedCell_shouldSetWorkerPositionEqualsToTheCell(){
        modelGame.addWorker(worker);
        state.executeState(modelGame, worker, cell);
        assertSame(cell, modelGame.getWorkerPosition(worker));
    }
}