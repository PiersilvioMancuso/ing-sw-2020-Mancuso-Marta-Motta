package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.model.god.Pan;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class UserTest {

    private User user;
    @Before
    public void setUp() {
        user = new User("StringTest");
    }

    @Test
    public void setUsername_NewName_ShouldChangeUsername() {
        user.setUsername("NewName");
        assertEquals("NewName", user.getUsername());
    }

    @Test
    public void setColor_GREEN_ShouldSetColorGreen() {
        user.setColor(Color.GREEN);
        assertEquals(Color.GREEN, user.getColor());
    }

}