package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.power.ApolloPower;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApolloTest {
    God god;

    @Test
    public void constructorTest(){
        god = new Apollo();
        assertTrue(god.getPower() instanceof ApolloPower);
    }

    @Test
    public void shouldBuild(){
        ModelGame modelGame = new ModelGame();
        User user = new User("Test");
        User user1 = new User("Test2");
        Worker worker = new Worker(user);

        modelGame.addWorker(worker);
        modelGame.addUser(user1);
        modelGame.setWorkerPosition(worker, new Cell(1,1));

        god = new Apollo();
        user.setGod(god);

        user.getGod().setUpTurn(modelGame, worker);

        user.getGod().executePower(modelGame, worker, new Cell(2,2));
        assertTrue(worker.getPosition().equals(new Cell(2,2)));

        user.getGod().executePower(modelGame,worker,new Cell(1,1));
        assertTrue(modelGame.getBoard().getCell(new Cell(1,1)).getHeight() == 1);
    }

}