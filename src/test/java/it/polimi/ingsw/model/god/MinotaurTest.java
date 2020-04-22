package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.power.MinotaurPower;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinotaurTest {
    God god;

    @Test
    public void constructorTest(){
        god = new Minotaur();
        assertTrue(god.getPower() instanceof MinotaurPower);
    }

}