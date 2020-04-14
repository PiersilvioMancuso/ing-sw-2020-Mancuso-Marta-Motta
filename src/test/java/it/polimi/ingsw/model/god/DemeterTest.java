package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.state.BuildState;
import it.polimi.ingsw.model.state.MovementState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DemeterTest {
    private God god;
    private User userTest;
    private Worker worker;
    private ModelGame model;
    private Cell cell;
    private Cell cell2;
    private Cell workerCell;

    @Before
    public void setUp() throws Exception {
        god = new Demeter();
        userTest = new User("userTest");
        worker = new Worker(userTest);
        model = new ModelGame();
        workerCell = new Cell(3,2,1);

        model.addWorker(worker);
        model.setWorkerPosition(worker, workerCell);

        god.power.setActiveEffect(true);
        god.setUpTurn(model, worker);
    }


    @Test
    public void isLoser_centerPosition_shouldLose() {
        model.setCurrentState(new MovementState());

        for (Cell boardCell : model.getBoard().getNeighbourCell(workerCell)){
            model.getBoard().getCell(boardCell).setHeight(3);
        }

        god.setUpTurn(model, worker);
        assertTrue(god.isLoser(model, worker));
    }


    @Test
    public void isLoser_centerPosition_shouldNotLose() {
        assertFalse(god.isLoser(model, worker));
    }


    @Test
    public void executePower_worker_shouldExecuteTheCompleteTurn() {
        cell = new Cell(3, 3, 1);
        cell2 = new Cell(2,2, 0);

        model.setCurrentState(new BuildState());

        god.power.runPower(model, worker, cell);
        assertEquals(2, model.getBoard().getCell(cell).getHeight());
        god.power.runPower(model, worker, cell2);
        assertEquals(1, model.getBoard().getCell(cell2).getHeight());
    }

    @Test(expected = IllegalArgumentException.class)
    public void executePower_worker_shouldNotBuildTwoBlockHigh() {
        cell = new Cell(3, 3, 1);
        cell2 = new Cell(2,2, 0);

        model.setCurrentState(new BuildState());

        god.power.runPower(model, worker, cell);
        assertEquals(2, model.getBoard().getCell(cell).getHeight());
        god.power.runPower(model, worker, cell);
        assertEquals(3, model.getBoard().getCell(cell).getHeight());
    }
}