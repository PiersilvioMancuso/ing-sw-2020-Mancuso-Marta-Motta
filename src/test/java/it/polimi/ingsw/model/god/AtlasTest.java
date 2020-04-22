package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.power.AtlasPower;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtlasTest {
    God god;

    @Test
    public void constructorTest(){
        god = new Atlas();
        assertTrue(god.getPower() instanceof AtlasPower);
    }

}