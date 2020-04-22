package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.model.Worker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AthenaPowerTest {
    Power power;
    ModelGame modelGame;
    Worker worker;
    Cell cell;

    @Before
    public void setUp() throws Exception {
        power = new AthenaPower();
        modelGame = new ModelGame();
        worker = new Worker(new User("Tested"));
        cell = new Cell(0,0);
        modelGame.addWorker(worker);
        modelGame.setWorkerPosition(worker,cell);
        power.setActiveEffect(true);
    }

    @Test
    public void runPower_notMovingUp_shouldNotTurnAthenaEffectOn() {
        assertFalse(Power.isAthenaEffect());
        power.startPower(modelGame,worker);
        power.runPower(modelGame,worker,new Cell(1,1));

        assertEquals(worker.getPosition(), new Cell(1, 1));
        assertFalse(Power.isAthenaEffect());
    }

    @Test
    public void runPower_movingUp_shouldTurnAthenaEffectOn() {
        assertFalse(Power.isAthenaEffect());
        Cell cell1 = new Cell(1,1,1);
        modelGame.getBoard().setCellBoard(cell1);
        power.startPower(modelGame,worker);
        power.runPower(modelGame,worker,cell1);

        assertEquals(worker.getPosition(), new Cell(1, 1));
        assertTrue(Power.isAthenaEffect());
    }

    @Test(expected = IllegalArgumentException.class)
    public void runPower_positionIsNotAValidCell_shouldThrowIllegalArgumentException() {
        assertFalse(Power.isAthenaEffect());
        Cell cell1 = new Cell(2,1);
        modelGame.getBoard().setCellBoard(cell1);
        power.startPower(modelGame,worker);
        power.runPower(modelGame,worker,cell1);
    }


}