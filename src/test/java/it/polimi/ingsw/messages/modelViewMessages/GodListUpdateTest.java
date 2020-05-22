package it.polimi.ingsw.messages.modelViewMessages;

import it.polimi.ingsw.messages.GodEnum;
import it.polimi.ingsw.model.god.Apollo;
import it.polimi.ingsw.model.god.Athena;
import it.polimi.ingsw.model.god.God;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GodListUpdateTest {
    GodListUpdate godListUpdate;

    @Before
    public void setUp() throws Exception {
        godListUpdate = new GodListUpdate(Arrays.asList(GodEnum.values()));
    }

    @Test
    public void getGods_afterCreation_shouldReturnAListOfAllGods() {
        List<God> godList = GodEnum.godValues();
        assertTrue(godList.containsAll(godListUpdate.getGods()));
        assertTrue(godListUpdate.getGods().containsAll(godList));
    }

    @Test
    public void setGods() {
        List<GodEnum> godList = new ArrayList<>();
        godList.add(GodEnum.APOLLO);
        godList.add(GodEnum.ATHENA);

        godListUpdate.setGods(godList);

        List<God> gods = new ArrayList<>();
        for (GodEnum godEnum : godList) gods.add(godEnum.getGod());
        assertEquals(gods, godListUpdate.getGods());

    }
}