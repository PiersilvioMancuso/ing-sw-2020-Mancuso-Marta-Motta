package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class HephaestusTest {
    private God god;
    private User userTest;
    private Worker worker;
    private ModelGame model;
    private Cell cell;
    private Cell workerCell;

    @Before
    public void setUp() throws Exception {
        god = new Hephaestus();
        userTest = new User("userTest");
        worker = new Worker(userTest);
        model = new ModelGame();
        workerCell = new Cell(3,2,1);

        model.addWorker(worker);
        model.setWorkerPosition(worker, workerCell);

        god.power.setActiveEffect(true);
        god.setUpTurn(model, worker);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void activatePower_powerShouldBeActive()  {
        god.activatePower(model, worker);
        assertTrue(god.power.isActiveEffect());
    }


    @Test
    public void isLoser_centerPosition_shouldLose() {
        for (Cell cell : model.getBoard().getNeighbourCell(workerCell)){
            model.getBoard().setCellHeight(cell, 3);
        }
        god.setUpTurn(model, worker);
        assertTrue(god.isLoser(model, worker));
    }

    @Test
    public void isLoser_centerPosition_shouldNotLose() {
        assertFalse(god.isLoser(model, worker));
    }

    @Test
    public void executePower_worker_shouldBuildTwoBlocks() {
        cell = new Cell(3, 3, 1);

        god.activatePower(model, worker);
        model.setCurrentState(new BuildState());

        model.getBoard().setCellBoard(cell);
        god.executePower(model, worker, cell);

        assertEquals(3, model.getBoard().getCell(cell).getHeight());
    }

    @Test
    public void executePower_worker_shouldMove() {
        cell = new Cell(3, 3, 1);

        god.activatePower(model, worker);
        god.executePower(model,worker, cell);

        assertEquals(cell, model.getWorkerPosition(worker));
    }

}