package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;

import it.polimi.ingsw.model.state.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PowerTest {
    Power power;
    Power power2;
    ModelGame modelGame;
    Worker worker;
    Cell cell;

    @Before
    public void setUp() throws Exception {
        power = new ApolloPower();
        power2 = new AthenaPower();
        power.setActiveEffect(false);
        modelGame = new ModelGame();
        worker = new Worker(new User("Test"));
        cell = new Cell(0,0,1);
        modelGame.getBoard().setCellBoard(cell);
        modelGame.addUser(new User("Helo"));
        modelGame.getBoard().setCellBoard(cell);
        modelGame.addWorker(worker);
        modelGame.setWorkerPosition(worker, cell);
    }

    @Test
    public void isAthenaEffect_hasBeenActivated_shouldReturnTrue() {
        power.setAthenaEffect(true);
        assertTrue(Power.isAthenaEffect());
    }

    @Test
    public void isActiveEffect_hasBeenActivated_shouldReturnTrue() {
        power.setActiveEffect(true);
        assertTrue(power.isActiveEffect());
    }

    @Test
    public void getCurrentState_onSetup_shouldReturnAnInstanceOfMovementState() {
        power.setStateList();
        assertTrue(power.getCurrentState() instanceof MovementState);
    }

    @Test
    public void setStateList_onSetup_shouldSetAListOfStateInPower() {
        power.setStateList();
        assertTrue(power.getStateList().get(0) instanceof MovementState);
        assertTrue(power.getStateList().get(1) instanceof BuildState);
        assertTrue(power.getStateList().get(2) instanceof EndState);

    }

    @Test
    public void setValidCells_onLeftUpsideCornerCellOnHeight2CloseToADomeOnCellAndWithAthenaEffectOff_validCellShouldHaveOnlyCellsOnItsRightAndBelow() {
        cell = new Cell(0,0,2);
        modelGame.getBoard().setCellBoard(cell);
        modelGame.setWorkerPosition(worker, cell);


        Cell cell1 = new Cell(1,1,4);
        Cell cell2 = new Cell(0,1);
        Cell cell3 = new Cell(1,0);
        modelGame.getBoard().setCellBoard(cell1);



        power.startPower(modelGame, worker);
        List<Cell> cells = new ArrayList<>();
        cells.add(cell3);
        cells.add(cell2);
        assertFalse(power.getValidCells(modelGame).contains(cell1));
        assertTrue(power.getValidCells(modelGame).containsAll(cells));

    }

    @Test
    public void setValidCells_onLeftUpsideCornerOnHeigh2CloseToAWorkerWithAthenaEffectOff_validCellShouldHaveOnlyCellsOnItsRightAndBelow(){

        cell = new Cell(0,0,2);
        modelGame.setWorkerPosition(worker, cell);

        Worker workerTest = new Worker(new User("userTest"));
        Cell cellTest = new Cell(1,1);
        modelGame.addWorker(workerTest);
        modelGame.setWorkerPosition(workerTest, cellTest);

        Cell cell2 = new Cell(0,1);
        Cell cell3 = new Cell(1,0);

        power.setActiveEffect(false);
        power.startPower(modelGame, worker);
        List<Cell> cells = new ArrayList<>();
        cells.add(cell3);
        cells.add(cell2);

        assertFalse(power.getValidCells(modelGame).contains(cellTest));
        assertTrue(power.getValidCells(modelGame).containsAll(cells));
        assertTrue(cells.containsAll(power.getValidCells(modelGame)));
    }


    @Test
    public void setValidCells_onLeftUpsideCornerOnFirstLevelWithAthenaEffectOn_validCellShouldHaveOnlyTheCellOnItsRight(){


        Cell cell1 = new Cell(0,1,2);
        modelGame.getBoard().setCellBoard(cell1);

        Worker workerTest = new Worker(new User("userTest"));
        Cell cellTest = new Cell(1,1);
        modelGame.getBoard().setCellBoard(cellTest);
        modelGame.addWorker(workerTest);
        modelGame.setWorkerPosition(workerTest, cellTest);

        Cell cell2 = new Cell(1,0,1);
        modelGame.getBoard().setCellBoard(cell2);

        power.setAthenaEffect(true);

        power.setActiveEffect(false);
        power.startPower(modelGame, worker);

        assertFalse(power.getValidCells(modelGame).contains(cellTest));
        assertFalse(power.getValidCells(modelGame).contains(cell1));
        assertTrue(power.getValidCells(modelGame).contains(cell2));

    }


    @Test
    public void setValidCells_onLeftUpsdeCornerOnFirstLevelWithAthenaEffectOn_validCellShouldHaveOnlyTheCellOnItsRight(){

        Cell cell1 = new Cell(0,1,2);
        modelGame.getBoard().setCellBoard(cell1);

        Worker workerTest = new Worker(new User("userTest"));
        Cell cellTest = new Cell(1,1);
        modelGame.addWorker(workerTest);
        modelGame.setWorkerPosition(workerTest, cellTest);

        Cell cell2 = new Cell(1,0,1);
        modelGame.getBoard().setCellBoard(cell2);

        power.setAthenaEffect(true);
        power.setActiveEffect(false);
        power.startPower(modelGame, worker);


        assertFalse(power.getValidCells(modelGame).contains(cellTest));
        assertFalse(power.getValidCells(modelGame).contains(cell1));
        assertTrue(power.getValidCells(modelGame).contains(cell2));

    }



    @Test
    public void setNextCurrentState_onMovementState_shouldSetCurrentStateAsBuildState() {
        power.startPower(modelGame, worker);

        assertTrue(modelGame.getCurrentState() instanceof MovementState);
        power.setNextCurrentState(modelGame);

        assertTrue(modelGame.getCurrentState() instanceof BuildState);

    }


    @Test
    public void runPower_onMovementState_shouldMoveTheWorker() {
        power.startPower(modelGame, worker);
        Cell cell1 = modelGame.getBoard().getCell(new Cell(1,1));
        power.runPower(modelGame, worker, cell1);

        assertEquals(worker.getPosition(), cell1);

    }

    @Test
    public void runPower_onBuildState_shouldIncreaseTheCellHeight() {
        power.startPower(modelGame, worker);
        Cell cell1 = modelGame.getBoard().getCell(new Cell(1,1));
        power.runPower(modelGame, worker, cell1);

        power.runPower(modelGame, worker, cell);

        assertEquals(2, modelGame.getBoard().getCell(cell).getHeight());
    }

    @Test
    public void isWinner_theWorkerMovedUpToTheThirdLevel_shouldReturnTrue() {
        Cell cell1 = new Cell(1,1,3);
        cell = new Cell(0,0,2);
        modelGame.getBoard().setCellBoard(cell);

        modelGame.getBoard().setCellBoard(cell1);
        modelGame.setWorkerPosition(worker, cell);

        power.startPower(modelGame, worker);
        power.runPower(modelGame, worker,cell1);

        assertTrue(power.isWinner(modelGame,worker,cell));

    }

    @Test
    public void isWinner_theWorkerMovedToTheThirdLevelFromTheThirdLevel_shouldReturnFalse(){
        cell.setHeight(3);
        modelGame.getBoard().setCellBoard(cell);


        Cell cell1 = new Cell(1,1,3);


        modelGame.getBoard().setCellBoard(cell1);
        modelGame.setWorkerPosition(worker, cell);

        power.startPower(modelGame, worker);
        power.runPower(modelGame, worker,cell1);

        assertFalse(power.isWinner(modelGame, worker, cell));

    }

    @Test
    public void setTextEffect_TestString_shouldSetTestAsPowerTextEffect() {
        power.setTextEffect("Test");
        assertEquals("Test", power.getTextEffect());
    }

    @Test
    public void setCurrentState_2_shouldSet2AsCurrentState() {
        power.startPower(modelGame,worker);
        power.setCurrentState(2);
        assertEquals(2, power.getStateList().indexOf(power.getCurrentState()));
    }

    @Test
    public void toString_TestString_shouldReturnTest() {
        power.setTextEffect("Test");
        assertEquals("Test", power.toString());
    }

    @Test
    public void setStateList_onAListContainigTwoMovementStateAndABuildStateAtTheEnd_shouldSetTheStateList() {
        List<State> states = new ArrayList<>();
        states.add(new MovementState());
        states.add(new MovementState());
        states.add(new BuildState());
        power.setStateList(states);
        assertEquals(states, power.getStateList());
    }

    @Test
    public void setValidCells_duringSetupStateWithAWorkerOn0_0Cell_shouldReturnAllBoardsCellsExceptThe0_0Cell() {
        modelGame.setCurrentState(new SetupState());
        power.setValidCells(modelGame, worker);
        List<Cell> validCells = modelGame.getBoard().getBuildMap();
        validCells.remove(0);
        assertTrue(modelGame.getValidCells().containsAll(validCells));
        assertTrue(validCells.containsAll(modelGame.getValidCells()));
    }
}