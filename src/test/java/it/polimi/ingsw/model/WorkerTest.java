package it.polimi.ingsw.model;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class WorkerTest {

    Worker worker = new Worker(Color.GREEN);

    @Test
    public void getColor() {
        assertEquals(Color.GREEN, worker.getColor());
    }
}