package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {
    private Cell cell;

    @Before
    public void setUp() throws Exception {
        cell = new Cell();
    }

    @Test
    public void getX_cellCreatedWithNoParameter_shouldReturnZero() {
        assertEquals(0, cell.getRow());
    }

    @Test
    public void getY_cellCreatedWithNoParameter_shouldReturnZero() {
        assertEquals(0, cell.getColumn());
    }

    @Test
    public void getHeight_cellCreatedWithNoParameter_shouldReturnZero() {
        assertEquals(0, cell.getHeight());
    }

    @Test
    public void setX_parameterEqualsToFive_shouldSetXFieldToFive() {
        cell.setRow(5);
        assertEquals(5, cell.getRow());
    }

    @Test
    public void setY_parameterEqualsToFive_shouldSetYFieldToFive() {
        cell.setColumn(5);
        assertEquals(5,cell.getColumn());
    }

    @Test
    public void setHeight_parameterEqualsToFive_shouldSetHeightFieldToFive() {
        cell.setHeight(5);
        assertEquals(5, cell.getHeight());
    }
}