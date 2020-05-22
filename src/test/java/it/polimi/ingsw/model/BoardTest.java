package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**Board Test Class
 * @author Piersilvio Mancuso
 */
public class BoardTest {
    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board();
    }

    // --------------- GETTER - TEST -----------------

    @Test
    public void getBuildMap_initializedBoard_shouldReturnAListOf25CellsWithHeightZero() {
        List<Cell> cellList = new ArrayList<>();

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){

                cellList.add(new Cell(i, j));
            }
        }
        assertEquals(25, board.getBuildMap().size());
        assertTrue(cellList.containsAll(board.getBuildMap()));
        assertTrue(board.getBuildMap().containsAll(cellList));

    }

    @Test
    public void getNeighbourCell_leftUpsideCornerCell_shouldReturnThreeCells() {
        Cell cell = new Cell(0,0);
        Cell cell1 = new Cell(0, 1);
        Cell cell2 = new Cell(1,0);
        Cell cell3 = new Cell(1,1);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        assertEquals(3, board.getNeighbourCell(cell).size());
        assertTrue(board.getNeighbourCell(cell).containsAll(cellList));
        assertTrue(cellList.containsAll(board.getNeighbourCell(cell)));

    }

    @Test
    public void getNeighbourCell_centerCell_shouldReturnEightCells() {
        Cell cell = new Cell(2,2);
        List<Cell> cellList = new ArrayList<>();
        for (int i = -1; i < 2; i++){

            for (int j = -1; j < 2; j++){

                if (i != 0 || j != 0){
                    cellList.add(new Cell(cell.getRow() + i ,cell.getColumn() + j));
                }
            }
        }

        assertEquals(8, board.getNeighbourCell(cell).size());
        assertTrue(board.getNeighbourCell(cell).containsAll(cellList));
        assertTrue(cellList.containsAll(board.getNeighbourCell(cell)));
    }

    @Test
    public void getBuildMap_shouldReturnTheBuildMap() {
        List<Cell> cellList = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5 ; j++){
                cellList.add(new Cell(i, j));
            }
        }

        assertTrue(cellList.containsAll(board.getBuildMap()));
        assertTrue(board.getBuildMap().containsAll(cellList));

    }

    // -------------------- SETTER - TEST -----------------


    @Test
    public void setCellBoard_originCellWithHeightThree_shouldSetOriginCellAsInputCell(){
        Cell cell = new Cell(0,0,3);
        board.setCellBoard(cell);
        assertEquals(cell, board.getCell(cell));
        assertEquals(cell.getHeight(), board.getCell(cell).getHeight());
    }

    @Test (expected = IllegalArgumentException.class)
    public void setCellBoard_cellNotInBoard_shouldThrowIllegalArgumentException(){
        Cell cell = new Cell(-1,1,1);
        board.setCellBoard(cell);
    }


    @Test
    public void setBuildMap_allCellsAtFirstLevel_shouldSetAllCellsOfTheBoardAtFirstLevel() {
        List<Cell> cellList = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5 ; j++){
                cellList.add(new Cell(i, j, 1));
            }
        }
        board.setBuildMap(cellList);
        assertEquals(cellList, board.getBuildMap());
    }
}