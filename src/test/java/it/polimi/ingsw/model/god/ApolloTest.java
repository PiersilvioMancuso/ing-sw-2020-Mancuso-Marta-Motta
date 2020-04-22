package it.polimi.ingsw.model.god;

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

}