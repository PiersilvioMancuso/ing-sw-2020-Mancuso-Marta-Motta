package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.power.ArtemisPower;
import it.polimi.ingsw.model.power.Power;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArtemisTest {
    God god;

    @Test
    public void constructorTest(){
        god = new Artemis();
        assertTrue(god.getPower() instanceof ArtemisPower);
    }

}