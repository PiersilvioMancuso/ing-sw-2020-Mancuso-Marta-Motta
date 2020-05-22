package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = new User("userTest");
        user.setColor(ModelColor.CYAN);
        user.setAge(33);
        user.setGodChosen(new Pan());
        user.setOutCome(OutCome.WINNER);
    }

    // -------------- GETTER - TEST ------------------


    @Test
    public void getUsername_userTest_shouldRespondWithUsername(){
        assertEquals("userTest", user.getUsername());
    }

    @Test
    public void getColor_CYAN_shouldSeeCyan(){
        assertEquals(ModelColor.CYAN, user.getColor());
    }

    @Test
    public void getAge_33_shouldBe33(){
        assertEquals(33, user.getAge());
    }

    @Test
    public void getGod_true_shouldBeInstanceOfGod(){
        assertTrue(user.getGod() instanceof Pan);
    }

    @Test
    public void getOutCome_WINNER_shouldWin(){
        assertEquals(OutCome.WINNER, user.getOutCome());
    }

    // -------------- SETTER - TEST ------------------


    @Test
    public void setUsername_newName_shouldChangeUsername() {
        user.setUsername("NewName");
        assertEquals("NewName", user.getUsername());
    }


    @Test
    public void setColor_GREEN_shouldSetColorGreen() {
        user.setColor(ModelColor.GREEN);
        assertEquals(ModelColor.GREEN, user.getColor());
    }


    @Test
    public void setAge_18_shouldBeAdult(){
        user.setAge(18);
        assertEquals(18, user.getAge());
    }


    @Test
    public void setGodChosen_true_shouldSetAtlasAsUserGod(){
        user.setGodChosen(new Atlas());
        assertTrue(user.getGod() instanceof Atlas);
    }


    @Test
    public void setOutCome_LOSER_shouldLose(){
        user = new User();
        user.setOutCome(OutCome.LOOSER);
        assertEquals(OutCome.LOOSER, user.getOutCome());
    }



    // ----------------- TO STRING SETTER ----------------------


    @Test
    public void toString_shouldPrintGodUsernameAndOutCome() {
        String tested = user.toString();
        String test = user.getUsername() + ":\t" + user.getGodChosen().getClass().getSimpleName() + " - " + user.getOutCome();
        assertEquals(tested, test);
    }
}