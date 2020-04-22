package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.power.AthenaPower;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AthenaTest {
    God god;
    ModelGame modelGame;
    Worker worker;

    @Before
    public void setUp() throws Exception {
        god = new Athena();
        modelGame = new ModelGame();
        worker = new Worker(new User("Tested"));
        modelGame.addWorker(worker);
        modelGame.setWorkerPosition(worker,new Cell(0,0));
    }

    @Test
    public void setUpTurn_onStart_shouldHavePowerActivated() {
        assertTrue(god.getPower() instanceof AthenaPower);

        god.setUpTurn(modelGame,worker);
        assertTrue(god.getPower().isActiveEffect());
    }
}