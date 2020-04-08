package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PowerTest {
    Power power;
    Power power2;
    ModelGame modelGame;
    Worker worker;
    Cell cell;

    @Before
    public void setUp() throws Exception {
        power = (Power) new ApolloPower();
        power2 = (Power) new AthenaPower();
    }

    @Test
    public void isAthenaEffect_hasBeenActivated_shouldReturnTrue() {
        power.setAthenaEffect(true);
        assertTrue(power.isAthenaEffect());
        assertTrue(power2.isAthenaEffect());
    }

    @Test
    public void isActiveEffect_hasBeenActivated_shouldReturnTrue() {
        power.setActiveEffect(true);
        assertTrue(power.isActiveEffect());
    }

    @Test
    public void getCurrentState_onSetup_shouldReturnAnInstanceOfMovementState() {
        power.setStateList();
        assertTrue(power.getCurrentState() instanceof MovementState);
    }

    @Test
    public void getValidCells() {
    }

    @Test
    public void setAthenaEffect() {
    }

    @Test
    public void setActiveEffect() {
    }

    @Test
    public void setStateList() {
    }

    @Test
    public void setValidCells() {
    }

    @Test
    public void athenaEffectModification() {
    }

    @Test
    public void setNextCurrentState() {
    }

    @Test
    public void startPower() {
    }

    @Test
    public void runPower() {
    }

    @Test
    public void isWinner() {
    }
}