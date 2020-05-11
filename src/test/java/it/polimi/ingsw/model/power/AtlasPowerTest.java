package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.model.Worker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtlasPowerTest {

    Power power;
    ModelGame modelGame;
    Worker worker;
    Cell cell;

    @Before
    public void setUp() throws Exception {
        power = new AtlasPower();
        modelGame = new ModelGame();
        worker = new Worker(new User("Tested"));
        cell = new Cell(0,0);
        modelGame.addWorker(worker);
        modelGame.setWorkerPosition(worker,cell);
        power.setActiveEffect(true);
        modelGame.addUser(new User("Helo"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void runPower_positionIsNotAValidCell_shouldThrowIllegalArgumentException() {
        power.startPower(modelGame,worker);
        power.runPower(modelGame,worker,new Cell(2,2));
    }

    @Test
    public void runPower_positionIsAValidCell_shouldBuildADome() {
        power.startPower(modelGame,worker);
        power.runPower(modelGame,worker,new Cell(1,1));
        Cell cellBuild = new Cell(1,0);
        modelGame.getBoard().setCellBoard(cellBuild);
        power.runPower(modelGame,worker,cellBuild);

        assertEquals(4, modelGame.getBoard().getCell(cellBuild).getHeight());
    }
}