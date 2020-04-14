package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.state.BuildState;
import it.polimi.ingsw.model.state.EndState;
import it.polimi.ingsw.model.state.MovementState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DemeterPowerTest {
    private Power power;
    private User userTest;
    private Worker worker;
    private Worker worker2;
    private ModelGame model;
    private Cell cell;
    private Cell workerCell;
    private Cell worker2Cell;

    @Before
    public void setUp() throws Exception {
        power = new DemeterPower();
        userTest = new User("userTest");
        worker = new Worker(userTest);
        model = new ModelGame();

        workerCell = new Cell(3,2,0);

        model.addWorker(worker);
        model.setWorkerPosition(worker, workerCell);
        power.startPower(model, worker);
    }

    @Test
    public void setStateList_shouldHaveTwoBuildStates() {
        assertTrue(power.stateList.get(0) instanceof MovementState &&
                power.stateList.get(1) instanceof BuildState &&
                power.stateList.get(2) instanceof BuildState &&
                power.stateList.get(3) instanceof EndState);
    }


    @Test
    public void startPower_worker_shouldInitializeTheTurn() {
        assertTrue(power.stateList.get(0) instanceof MovementState &&
                power.stateList.get(1) instanceof BuildState &&
                power.stateList.get(2) instanceof BuildState &&
                power.stateList.get(3) instanceof EndState);

        assertTrue(power.getValidCells().size() > 0);
    }


    @Test
    public void runPower_worker_shouldBuild() {
        cell = new Cell(3, 3, 1);
        model.getBoard().setCellBoard(cell);

        model.setCurrentState(new BuildState());

        power.runPower(model, worker, cell);
        assertEquals(2, cell.getHeight());

    }


    @Test(expected = IllegalArgumentException.class)
    public void runPower_worker_shouldNotBuildOnWorker(){
        worker2Cell = new Cell(3,3,1);
        model.getBoard().setCellBoard(worker2Cell);

        worker2 = new Worker(userTest);
        model.addWorker(worker2);
        model.setWorkerPosition(worker2, worker2Cell);

        power.startPower(model, worker);
        model.setCurrentState(new BuildState());

        power.runPower(model, worker, worker2Cell);
    }


    @Test
    public void runPower_worker_shouldMove() {
        cell = new Cell(3, 3, 1);
        model.getBoard().setCellBoard(cell);

        model.setCurrentState(new MovementState());

        power.runPower(model, worker, cell);
        assertEquals(cell, model.getWorkerPosition(worker));

    }


    @Test(expected = IllegalArgumentException.class)
    public void runPower_worker_shouldNotMoveOnWorker(){
        worker2Cell = new Cell(3,3,1);
        model.getBoard().setCellBoard(worker2Cell);

        worker2 = new Worker(userTest);
        model.addWorker(worker2);
        model.setWorkerPosition(worker2, worker2Cell);

        power.startPower(model, worker);
        model.setCurrentState(new MovementState());

        power.runPower(model, worker, worker2Cell);
    }
}