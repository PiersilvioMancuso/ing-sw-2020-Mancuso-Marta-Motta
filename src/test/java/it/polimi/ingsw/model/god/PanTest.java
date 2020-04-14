package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.state.BuildState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PanTest {
    private God god;
    private User userTest;
    private Worker worker;
    private ModelGame model;
    private Cell cell;
    private Cell workerCell;

    @Before
    public void setUp() throws Exception {
        god = new Pan();
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
    public void activatePower_powerShouldBeActive() {
        god.activatePower(model, worker);
        assertTrue(god.power.isActiveEffect());
    }


    @Test
    public void isLoser_worker_shouldLose() {
        for (Cell cell : model.getBoard().getNeighbourCell(workerCell)){
            model.getBoard().setCellHeight(cell, 3);
        }
        god.setUpTurn(model, worker);
        assertTrue(god.isLoser(model, worker));
    }


    @Test
    public void isLoser_worker_shouldNotLose() {
        assertFalse(god.isLoser(model, worker));
    }

    @Test
    public void executePower_worker_shouldBuild() {
        cell = new Cell(3, 3, 1);

        model.setCurrentState(new BuildState());
        model.getBoard().setCellBoard(cell);

        god.activatePower(model, worker);
        god.executePower(model, worker, cell);
        assertEquals(2, model.getBoard().getCell(cell).getHeight());
    }

    @Test
    public void executePower_worker_shouldMove() {
        god.activatePower(model, worker);

        god.power.runPower(model, worker, new Cell(2, 2, 1));
        assertEquals(new Cell(2, 2, 1), model.getWorkerPosition(worker));
    }
    
}