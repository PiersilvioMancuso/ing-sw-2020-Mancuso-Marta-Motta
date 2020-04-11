package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class WorkerTest {

    private User testUser;
    private Worker testWorker;

    @Before
    public void setUp() throws Exception {
        testUser = new User("testUser");
        testUser.setColor(Color.GREEN);
        testWorker = new Worker(testUser);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getUser_testUser_shouldBePairedWithTestUser() {
        assertEquals(testUser, testWorker.getUser());
    }

    @Test
    public void setUser_newUser_shouldBePairedWithNewUser() {
        User newUser = new User("newUser");
        newUser.setColor(Color.CYAN);
        testWorker.setUser(newUser);
        assertEquals(newUser, testWorker.getUser());
    }

    @Test
    public void getColor_GREEN_shouldBeGreen() {
        assertEquals(Color.GREEN, testWorker.getColor());
    }

    @Test
    public void setColor_CYAN_shouldBeCyan() {
        testWorker.setColor(Color.CYAN);
        assertEquals(Color.CYAN, testWorker.getColor());
    }
}