package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.model.Worker;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MinotaurPowerTest {
    MinotaurPower power;
    ModelGame modelGame;
    Worker worker;
    Cell cell;
    User user;

    @Before
    public void setUp() throws Exception {
        power = new MinotaurPower();
        modelGame = new ModelGame();
        user = new User("tested");
        worker = new Worker(user);
        cell = new Cell(0,0);
        power.setActiveEffect(true);
        modelGame.addWorker(worker);
        modelGame.setWorkerPosition(worker, cell);
    }

    @Test
    public void minotaurCalculator_fromLeftUpsideCornerToRightDownsideCornerDirection_shouldReturnTheMiddleCell(){
        Cell cellMinotaur = new Cell(2,2);
        Cell tested = new Cell(1,1);

        Cell res = power.minotaurCalculator(cell,tested);

        assertEquals(cellMinotaur,res);

    }

    @Test
    public void setValidCells_onLeftUpsideCornerWithAWorkerFromDifferentUserOnRightWithAFreeCellOnTheSameDirectionAndADomeBelow_shouldHaveJustTheWorkerOnHisRightAndTheFreeCellAsValidCells() {

        Cell cell2 = new Cell(0,1,4);
        modelGame.getBoard().setCellBoard(cell2);

        Worker workerTest = new Worker(new User("test"));
        modelGame.addWorker(workerTest);
        Cell cell3 = new Cell(1,0);
        workerTest.setPosition(cell3);

        List<Cell> cells = new ArrayList<>();
        cells.add(cell3);
        cells.add(new Cell(1,1));

        power.startPower(modelGame, worker);
        assertTrue(!power.getValidCells(modelGame).contains(cell2));
        assertTrue(power.getValidCells(modelGame).containsAll(cells));
        assertTrue(cells.containsAll(power.getValidCells(modelGame)));

    }

    @Test
    public void setValidCells_onLeftUpsideCornerWithAWorkerFromDifferentUserOnRightWithAnOtherWorkerCellOnTheSameDirectionAndADomeBelow_shouldHaveJustTheWorkerOnHisRightAndTheFreeCellAsValidCells() {

        Cell cell2 = new Cell(0,1,4);
        modelGame.getBoard().setCellBoard(cell2);

        Worker workerTest = new Worker(new User("test"));
        modelGame.addWorker(workerTest);
        Cell cell3 = new Cell(1,0);
        workerTest.setPosition(cell3);

        Worker workerTest2 = new Worker(new User("test"));
        modelGame.addWorker(workerTest2);
        Cell cell4 = new Cell(2,0);
        workerTest2.setPosition(cell4);

        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(1,1));

        power.startPower(modelGame, worker);
        assertTrue(!power.getValidCells(modelGame).contains(cell2));
        assertTrue(power.getValidCells(modelGame).containsAll(cells));
        assertTrue(cells.containsAll(power.getValidCells(modelGame)));

    }


    @Test
    public void setValidCells_onLeftUpsideCornerWithAWorkerFromTheSameUserOnRightAndADomeBelow_shouldHaveJustTheFreeCellAsValidCell() {

        Cell cell2 = new Cell(0,1,4);
        modelGame.getBoard().setCellBoard(cell2);

        Worker workerTest = new Worker(user);
        modelGame.addWorker(workerTest);
        Cell cell3 = new Cell(1,0);
        workerTest.setPosition(cell3);

        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(1,1));

        power.startPower(modelGame, worker);
        assertTrue(!power.getValidCells(modelGame).contains(cell2));
        assertTrue(!power.getValidCells(modelGame).contains(cell3));
        assertTrue(power.getValidCells(modelGame).containsAll(cells));
        assertTrue(cells.containsAll(power.getValidCells(modelGame)));

    }

    @Test
    public void setValidCells_onLeftUpsideCornerWithAADomeBelow_shouldHaveJustTheFreeCellsAsValidCells() {

        Cell cell2 = new Cell(0,1,4);
        modelGame.getBoard().setCellBoard(cell2);

        Cell cell3 = new Cell(1,0);

        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(1,1));
        cells.add(cell3);

        power.startPower(modelGame, worker);
        assertTrue(!power.getValidCells(modelGame).contains(cell2));
        assertTrue(power.getValidCells(modelGame).containsAll(cells));
        assertTrue(cells.containsAll(power.getValidCells(modelGame)));

    }


    @Test
    public void runPower_movingToAnotherWorkerCell_shouldPushOtherWorker() {

        Worker workerTest = new Worker(new User("test"));
        modelGame.addWorker(workerTest);
        Cell cell3 = new Cell(1,0);
        workerTest.setPosition(cell3);

        Cell cell4 = power.minotaurCalculator(cell,cell3);

        power.startPower(modelGame,worker);
        power.runPower(modelGame,worker,cell3);

        assertTrue(worker.getPosition().equals(cell3));
        assertTrue(workerTest.getPosition().equals(cell4));

    }

    @Test(expected = IllegalArgumentException.class)
    public void runPower_movingToAnotherWorkerFromTheSameUserCell_shouldThrowIllegalArgumentException() {

        Worker workerTest = new Worker(user);
        modelGame.addWorker(workerTest);
        Cell cell3 = new Cell(1,0);
        workerTest.setPosition(cell3);

        power.startPower(modelGame,worker);
        power.runPower(modelGame,worker,cell3);

    }
}