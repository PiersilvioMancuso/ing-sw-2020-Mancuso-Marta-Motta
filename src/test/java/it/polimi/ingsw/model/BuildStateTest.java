package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BuildStateTest {

    BuildState state;
    ModelGame modelGame;
    Worker worker;
    Cell cell;

    @Before
    public void setUp() throws Exception {
        state = new BuildState();
        modelGame = new ModelGame();
        worker = new Worker(new User("Test"));
        cell = new Cell();
    }

    @Test(expected = NullPointerException.class)
    public void executeState_nullArgument_shouldThrowsNullPointerException() {
        state.executeState(modelGame,null,cell);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeState_outOfBoardCell_shouldThrowIllegalArgumentException(){

        cell = new Cell(-1,1,1);
        modelGame.addWorker(worker);
        state.executeState(modelGame, worker, cell);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeState_domeCell_shouldThrowIllegalArgumentException(){

        cell = new Cell(1,1,4);
        modelGame.addWorker(worker);
        state.executeState(modelGame, worker, cell);
    }


    @Test
    public void executeState_unoccupiedCell_shouldSetWorkerPositionEqualsToTheCell(){
        modelGame.addWorker(worker);
        int height = cell.getHeight();
        state.executeState(modelGame, worker, cell);

        assertSame(1, modelGame.getBoard().getCell(cell).getHeight());
    }
}