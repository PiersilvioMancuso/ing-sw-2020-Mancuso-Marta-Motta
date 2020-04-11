package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrometheusPowerTest {
    private Power power;
    private User userTest;
    private Worker worker;
    private Worker worker2;
    private ModelGame model;
    private Cell workerCell;
    private Cell worker2Cell;
    private Cell cell;

    @Before
    public void setUp() throws Exception {
        power = new PrometheusPower();
        userTest = new User("userTest");
        worker = new Worker(userTest);
        model = new ModelGame();

        workerCell = new Cell(3,3,1);
        cell = new Cell(3, 1, 0);

        model.addWorker(worker);
        model.setWorkerPosition(worker, workerCell);
        power.startPower(model, worker);
    }

    @Test
    public void setStateList_shouldContainTwoBuildStates() {
        assertTrue(power.stateList.get(0) instanceof BuildState &&
                power.stateList.get(1) instanceof MovementState &&
                power.stateList.get(2) instanceof BuildState &&
                power.stateList.get(3) instanceof EndState);
    }

    @Test
    public void setValidCells_centerPosition_validCellsShouldHaveAtLeastOneCell() {
        model.setCurrentState(new MovementState());
        model.getBoard().setCellBoard(cell);

        power.startPower(model, worker);
        assertTrue(power.getValidCells().size()>0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void setValidCells_centerPosition_validCellsShouldHaveAtLeastOneCellNotContainDomeOrWorkerCells() {
        worker2 = new Worker(userTest);
        worker2Cell = new Cell(2,3,1);
        model.addWorker(worker2);
        model.setWorkerPosition(worker2, worker2Cell);

        cell = new Cell(3,2,4);
        model.getBoard().setCellBoard(cell);

        model.setCurrentState(new MovementState());

        power.startPower(model, worker);
        power.runPower(model, worker, cell);
        assertTrue(power.getValidCells().size()>0);
        assertFalse(power.getValidCells().contains(cell));
        assertFalse(power.getValidCells().contains(worker2Cell));
    }
}