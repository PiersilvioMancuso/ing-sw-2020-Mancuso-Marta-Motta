package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board implements Serializable {
    private List<Cell> buildMap;

    public Board() {
        buildMap = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                buildMap.add(new Cell(i, j));
            }
        }
    }

    /**Get the current map of the game
     * @author Piersilvio Mancuso
     * @return a List of Cell in the board
     */
    public List<Cell> getBuildMap() {
        return buildMap;
    }


    /**Get the cell of the game
     * @author Piersilvio Mancuso
     * @param cell is the position of the cell searched
     * @return the cell of the game
     */
    public Cell getCell(Cell cell){
        Cell res = new Cell();

        for (int i = 0; i < buildMap.size(); i++){
            if (buildMap.get(i).equals(cell)) res = buildMap.get(i);
        }
        return res;

    }




    /**
     * get Neighbour Cell
     * @author Motta
     * @param cell is a point of the map
     * @return a list of cells of the board adjacent to the input cell
     */
    public List <Cell> getNeighbourCell(Cell cell) {
        List <Cell> res = new ArrayList<>();

        for (Cell position : buildMap){
            if (Math.abs(cell.getX() - position.getX()) <= 1 && Math.abs(cell.getY() - position.getY()) <= 1){
                if (!position.equals(cell)){
                    res.add(position);
                }
            }
        }

        return res;
    }

    public void setCellHeight(Cell cell, int height){
        for (Cell buildCell : buildMap) {
            if (buildCell.equals(cell))
                buildCell.setHeight(height);
        }
    }


    /**Set in the board the new value of Cell
     * @author Piersilvio Mancuso
     * @param cell is the Cell object that will be assigned into the board
     */
    public void setCellBoard(Cell cell){
        if (!buildMap.contains(cell)) throw new IllegalArgumentException("Cell is not in Board");
        for (int i = 0; i< buildMap.size(); i++){
            if (buildMap.get(i).equals(cell)) buildMap.set(i, new Cell(cell));
        }
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board board = (Board) o;
        return Objects.equals(getBuildMap(), board.getBuildMap());
    }


}
