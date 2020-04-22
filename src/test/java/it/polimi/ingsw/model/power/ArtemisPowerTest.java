package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.state.MovementState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArtemisPowerTest {
    Power power;
    ModelGame modelGame;
    Worker worker;
    User user;
    Cell cell;

    @Before
    public void setUp() throws Exception {
        power = new ArtemisPower();
        modelGame = new ModelGame();
        user = new User("Tested");
        worker = new Worker(user);
        cell = new Cell(0,0);
        power.setActiveEffect(true);
        modelGame.addWorker(worker);
        modelGame.setWorkerPosition(worker,cell);
    }

    @Test
    public void setStateList_onStart_shouldHaveTwoMovementStates() {
        power.setStateList();
        int count = 0;
        for (int i = 0; i< power.getStateList().size(); i++){
            if (power.getStateList().get(i) instanceof MovementState) count++;
        }

        assertTrue(count == 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void runPower_onSecondMovement_shouldNotBeAbleToMoveOnSecondMovementToHisFirstPosition() {

        power.startPower(modelGame,worker);
        Cell cell1 = new Cell(1,1);
        power.runPower(modelGame,worker,cell1);
        power.runPower(modelGame,worker,cell);
    }

    @Test
    public void runPower_onSecondMovement_shouldNotBeAbleToMoveTwoTimes() {

        power.startPower(modelGame,worker);
        Cell cell1 = new Cell(1,1);
        power.runPower(modelGame,worker,cell1);

        assertTrue(worker.getPosition().equals(cell1));

        power.runPower(modelGame,worker,new Cell(2,2));
        assertTrue(worker.getPosition().equals(new Cell(2,2)));
    }
}