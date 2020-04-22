package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.power.Power;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        assertTrue(god.getPower().getValidCells() != null);
        assertTrue(god.getPower().getStateList().size() == 3);
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

        assertTrue(!god.isLoser(modelGame,worker));

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

    @After
    public void reset(){
        god.getPower().setActiveEffect(false);
    }
}