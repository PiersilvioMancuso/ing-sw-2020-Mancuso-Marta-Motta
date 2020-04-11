package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OutComeTest {

    @Test
    public void winsIfTrue_true_shouldReturnWINNER() {
        assertEquals(OutCome.WINNER, OutCome.winsIfTrue(true));
    }

    @Test
    public void winsIfTrue_false_shouldReturnDRAW() {
        assertEquals(OutCome.DRAW, OutCome.winsIfTrue(false));
    }

    @Test
    public void looseIfTrue_true_shouldReturnLOOSER() {
        assertEquals(OutCome.LOOSER, OutCome.looseIfTrue(true));
    }

    @Test
    public void looseIfTrue_false_shouldReturnDRAW() {
        assertEquals(OutCome.DRAW, OutCome.looseIfTrue(false));
    }
}