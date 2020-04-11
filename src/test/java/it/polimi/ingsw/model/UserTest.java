package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = new User("userTest");
        user.setColor(Color.CYAN);
        user.setAge(33);
        user.setGod(new Pan());
        user.setOutCome(OutCome.WINNER);
    }

    @Test
    public void getUsername_userTest_shouldRespondWithUsername(){
            assertEquals("userTest", user.getUsername());
    }

    @Test
    public void setUsername_newName_shouldChangeUsername() {
        user.setUsername("NewName");
        assertEquals("NewName", user.getUsername());
    }

    @Test
    public void getColor_CYAN_shouldSeeCyan(){
        assertEquals(Color.CYAN, user.getColor());
    }

    @Test
    public void setColor_GREEN_shouldSetColorGreen() {
        user.setColor(Color.GREEN);
        assertEquals(Color.GREEN, user.getColor());
    }

    @Test
    public void getAge_33_shouldBe33(){
        assertEquals(33, user.getAge());
    }

    @Test
    public void setAge_18_shouldBeAdult(){
        user.setAge(18);
        assertEquals(18, user.getAge());
    }

    @Test
    public void getGod_true_shouldBeInstanceOfGod(){
        assertTrue(user.getGod() instanceof Pan);
    }

    @Test
    public void setGod_true_shouldSetAtlasAsUserGod(){
        user.setGod(new Atlas());
        assertTrue(user.getGod() instanceof Atlas);
    }

    @Test
    public void getOutCome_WINNER_shouldWin(){
        assertEquals(OutCome.WINNER, user.getOutCome());
    }

    @Test
    public void setOutCome_LOSER_shouldLose(){
        user.setOutCome(OutCome.LOOSER);
        assertEquals(OutCome.LOOSER, user.getOutCome());
    }

}