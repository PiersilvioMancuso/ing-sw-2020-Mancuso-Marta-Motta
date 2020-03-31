package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OutComeTest {

    @Test
    public void winsIfTrue_True_ShouldReturnWINNER() {
        assertEquals(OutCome.WINNER, OutCome.winsIfTrue(true));
    }

    @Test
    public void winsIfTrue_False_ShouldReturnDRAW() {
        assertEquals(OutCome.DRAW, OutCome.winsIfTrue(false));
    }

    @Test
    public void looseIfTrue_True_ShouldReturnLOOSER() {
        assertEquals(OutCome.LOOSER, OutCome.looseIfTrue(true));
    }

    @Test
    public void looseIfTrue_False_ShouldReturnDRAW() {
        assertEquals(OutCome.DRAW, OutCome.looseIfTrue(false));
    }
}