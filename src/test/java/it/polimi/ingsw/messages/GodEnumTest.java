package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.god.Apollo;
import it.polimi.ingsw.model.god.God;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GodEnumTest {

    @Test
    public void getGod_onGodEnumApollo_shouldReturnAnInstanceOfApollo() {
        assertTrue(GodEnum.APOLLO.getGod() instanceof Apollo);
    }

    @Test
    public void godValues_shouldReturnAllGodsInGodEnum() {
        List<God> godList = new ArrayList<>();
        for (GodEnum godEnum : GodEnum.values()){
            godList.add(godEnum.getGod());
        }
        assertTrue(godList.containsAll(GodEnum.godValues()));
        assertTrue(GodEnum.godValues().containsAll(godList));
    }
}