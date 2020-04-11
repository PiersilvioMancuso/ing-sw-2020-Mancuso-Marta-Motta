package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrometheusTest {
    private God god;
    private User userTest;
    private Worker worker;
    private ModelGame model;
    private Cell cell;
    private Cell cell2;
    private Cell cell3;
    private Cell workerCell;

    @Before
    public void setUp() throws Exception {
        god = new Prometheus();
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
    public void activatePower_powerShouldBeActive()  {
        god.activatePower(model, worker);
        assertTrue(god.power.isActiveEffect());
    }


    @Test
    public void isLoser_true_shouldLose() {
        for (Cell cell : model.getBoard().getNeighbourCell(workerCell)){
            model.getBoard().setCellHeight(cell, 3);
        }
        god.setUpTurn(model, worker);
        assertTrue(god.isLoser(model, worker));
    }


    @Test
    public void isLoser_false_shouldNotLose() {
        assertFalse(god.isLoser(model, worker));
    }


    @Test
    public void executePower_worker_shouldBuildMoveBuild() {
        cell = new Cell(2,2,0);
        cell2 = new Cell(3,3,0);
        cell3 = new Cell(3,4,1);

        model.getBoard().setCellBoard(cell);
        model.getBoard().setCellBoard(cell2);
        model.getBoard().setCellBoard(cell3);

        god.power.startPower(model, worker);

        god.power.runPower(model, worker, cell);
        assertEquals(1, model.getBoard().getCell(cell).getHeight());

        god.power.runPower(model, worker, cell2);
        assertEquals(cell2, model.getWorkerPosition(worker));

        god.power.runPower(model, worker, cell3);
        assertEquals(2, model.getBoard().getCell(cell3).getHeight());
    }


    @Test(expected = IllegalArgumentException.class)
    public void executePower_worker_shouldBuildAndNotMove() {
        cell = new Cell(2, 2, 0);
        cell2 = new Cell(3, 3, 3);

        model.getBoard().setCellBoard(cell);
        model.getBoard().setCellBoard(cell2);

        god.power.startPower(model, worker);

        god.power.runPower(model, worker, cell);
        god.power.runPower(model, worker, cell2);
    }

}