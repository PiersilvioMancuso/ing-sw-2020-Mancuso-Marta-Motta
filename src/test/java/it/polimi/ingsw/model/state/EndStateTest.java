package it.polimi.ingsw.model.state;

import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.god.Apollo;
import it.polimi.ingsw.model.state.EndState;
import it.polimi.ingsw.model.state.State;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EndStateTest {
    State state;
    ModelGame modelGame;
    Worker worker;
    User user;

    @Before
    public void setUp() throws Exception {
        user = new User("Test");
        modelGame = new ModelGame();
        worker = new Worker(user);
        modelGame.addWorker(worker);
        user.setGod(new Apollo());
        state = new EndState();
    }

    @Test (expected = NullPointerException.class)
    public void executeState_nullParameters_shouldThrowNullPointerException() {
        state.executeState(modelGame,null,null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void executeState_workerNotInGame_shouldThrowIllegalArgumentException(){
        state.executeState(modelGame, new Worker(user), null);
    }

    @Test
    public void executeState_endingTurn_shouldSetNextUser(){
        modelGame.addUser(user);
        User user1 = new User("Test1");
        modelGame.addUser(user1);

        state.executeState(modelGame, worker, null);
        assertTrue(modelGame.getCurrentUser() == modelGame.getUserList().get(1));
    }

}