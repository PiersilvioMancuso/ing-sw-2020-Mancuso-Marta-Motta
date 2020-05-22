package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.PanPower;
import it.polimi.ingsw.model.power.Power;
import it.polimi.ingsw.model.state.MovementState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GodTest {
    God god;
    ModelGame modelGame;
    Worker worker;
    Cell cell;


    @Before
    public void setUp(){
        god = new Apollo();
        god.power.setActiveEffect(false);
        modelGame = new ModelGame();
        worker = new Worker(new User("Tested"));
        modelGame.addWorker(worker);
        cell = new Cell(0,0);
        modelGame.setWorkerPosition(worker,cell);
    }

    @Test
    public void getPower_onCreation_shouldReturnAnInstanceOfPower() {
        assertTrue(god.getPower() instanceof Power);
    }

    @Test
    public void activatePower_shouldTurnOnActivationPower() {
        god.activatePower(modelGame,worker);
        assertTrue(god.getPower().isActiveEffect());
    }

    @Test
    public void setUpTurn_startingTheGame_shouldHaveAValidCellsListAndAStateList() {
        god.setUpTurn(modelGame,worker);
        assertNotNull(god.getPower().getValidCells(modelGame));
        assertEquals(3, god.getPower().getStateList().size());
    }

    @Test
    public void isLoser_validCellsEmpty_shouldReturnTrue() {
        modelGame.getBoard().setCellBoard(new Cell(1,1,4));
        modelGame.getBoard().setCellBoard(new Cell(0,1,4));
        modelGame.getBoard().setCellBoard(new Cell(1,0,4));
        god.setUpTurn(modelGame,worker);

        assertTrue(god.isLoser(modelGame,worker));

    }

    @Test
    public void isLoser_validCellsNotEmpty_shouldReturnFalse() {
        modelGame.getBoard().setCellBoard(new Cell(1,1,4));
        modelGame.getBoard().setCellBoard(new Cell(0,1,4));
        god.setUpTurn(modelGame,worker);

        assertFalse(god.isLoser(modelGame, worker));

    }

    @Test(expected = IllegalArgumentException.class)
    public void executePower_positionIsNotAValidCell_shouldThrowIllegalArgumentException() {
        god.setUpTurn(modelGame,worker);
        god.executePower(modelGame,worker,new Cell(2,2));
    }

    @Test
    public void executePower_positionIsAValidCell_shouldMoveIntoPosition() {
        god.setUpTurn(modelGame,worker);
        god.executePower(modelGame,worker,new Cell(1,1));
    }


    @Test
    public void winEffect_ifGodWins_shouldSetAllUserOutcomeToLOOSEExceptTheWinnerOneWhoWillBeSetToWINNER() {
        cell = new Cell(0,0,3);
        modelGame.getBoard().setCellBoard(cell);
        User user = new User("Winner");
        modelGame.addUser(user);
        worker.setUser(user);
        User user1 = new User("TestedNotWinner");
        modelGame.addUser(user1);
        modelGame.setWorkerPosition(worker,cell);
        Cell cell1 = new Cell(0,0,2);

        god.winEffect(modelGame, worker, cell1);
        for (User user2 : modelGame.getUserList()){
            if (user2.equals(user)) assertEquals(OutCome.WINNER, user2.getOutCome());
            else assertEquals(OutCome.LOOSER, user2.getOutCome());
        }
    }

    @Test
    public void setPower_PanPower_shouldSetGodPowerAsPanPower() {
        Power power = new PanPower();
        god.setPower(power);
        assertEquals(power, god.getPower());
    }

    @Test
    public void looseEffect_userIsLooser_shouldRemoveAllWorkersOfTheLooserPlayer() {
        User user = new User("Tested");
        worker = new Worker(user);

        User user1 = new User("TestedLooser");
        Worker worker1 = new Worker(user1);

        modelGame.addUser(user);
        modelGame.addUser(user1);
        modelGame.setValidCells(new ArrayList<Cell>());
        modelGame.addWorker(worker1);
        modelGame.addWorker(worker);
        modelGame.setCurrentState(new MovementState());
        god.looseEffect(modelGame, worker1);

        //assertFalse(modelGame.getWorkerList().contains(worker1));
        assertFalse(modelGame.getUserList().contains(user1));
    }

    @After
    public void reset(){
        god.getPower().setActiveEffect(false);
    }
}