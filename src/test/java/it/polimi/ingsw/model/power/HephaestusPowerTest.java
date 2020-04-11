package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HephaestusPowerTest {
    private Power power;
    private User userTest;
    private Worker worker;
    private ModelGame model;
    private Cell cell;
    private Cell endCell;
    private Cell workerCell;

    @Before
    public void setUp() throws Exception {
        power = new HephaestusPower();
        userTest = new User("userTest");
        worker = new Worker(userTest);
        model = new ModelGame();

        workerCell = new Cell(3,2,0);

        model.addWorker(worker);
        model.setWorkerPosition(worker, workerCell);
        power.startPower(model, worker);
    }

    //Test sia in movement che in build states
    @Test
    public void runPower_3_shouldBuildTwoBlocks() {
        cell = new Cell(3, 3, 1);
        model.getBoard().setCellBoard(cell);
        model.setCurrentState(new BuildState());
        power.runPower(model, worker, cell);

        assertEquals(3, model.getBoard().getCell(cell).getHeight());
    }

    @Test
    public void runPower_x2y2_shouldMove() {
        endCell = new Cell(2, 2, 1);
        model.setWorkerPosition(worker, workerCell);
        model.setCurrentState(new MovementState());
        power.runPower(model, worker, endCell);
        assertEquals(endCell, model.getWorkerPosition(worker));
    }
}