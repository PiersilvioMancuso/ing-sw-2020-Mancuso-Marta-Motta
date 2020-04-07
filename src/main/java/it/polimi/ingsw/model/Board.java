package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Cell> buildMap;

    public Board() {
        buildMap = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                buildMap.add(new Cell(i, j));
            }
        }
    }


    public List<Cell> getBuildMap() {
        return buildMap;
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


}
