package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.model.god.Pan;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PanPowerTest {
    private Power power;
    private User userTest;
    private Worker worker;
    private ModelGame model;
    private Cell cell;
    private Cell workerCell;
    private Cell endingCell;

    @Before
    public void setUp() throws Exception {
        power = new PanPower();
        userTest = new User("userTest");
        worker = new Worker(userTest);
        model = new ModelGame();
        workerCell = new Cell(3, 2, 2);

        model.addWorker(worker);
        model.setWorkerPosition(worker, workerCell);

        power.setActiveEffect(true);
        power.startPower(model, worker);
    }

    @Test
    public void setActiveEffect_true_shouldBeActive() {
        assertTrue(power.isActiveEffect());
    }

    @Test
    public void isWinner_worker_shouldWin() {
        endingCell = new Cell(3, 1, 3);
        model.getBoard().setCellBoard(endingCell);

        power.runPower(model, worker, endingCell);
        assertTrue(power.isWinner(model, worker, workerCell));
    }

    @Test
    public void isWinner_worker_shouldWinAsPan() {
        endingCell = new Cell(3, 1, 0);
        model.getBoard().setCellBoard(endingCell);

        power.runPower(model, worker, endingCell);
        assertTrue(power.isWinner(model, worker, workerCell));
    }

    @Test
    public void isWinner_worker_shouldNotWin() {
        endingCell = new Cell(3, 1, 2);
        model.getBoard().setCellBoard(endingCell);

        power.runPower(model, worker, endingCell);
        assertFalse(power.isWinner(model, worker, workerCell));
    }
}