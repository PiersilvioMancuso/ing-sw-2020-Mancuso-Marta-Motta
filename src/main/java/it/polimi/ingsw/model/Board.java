package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] buildMap;

    public Board() {
        this.buildMap=new int[5][5];
    }


    public int[][] getBoard() {
        return buildMap;
    }


    /**
     * get cell's Build Height
     * @author Motta
     * @param cell
     * @return
     */
    public int getBuildHeight(int[] cell) {
        return buildMap[cell[0]][cell[1]];
    }

    /**
     * set cell's Build Height
     * @author Motta
     * @param cell
     * @param height
     */
    public void setBuildHeight(int[] cell, int height) {
        if (cell[0]<0 || cell[0]>4 || cell[1]<0 || cell[1]>4) throw new ArrayIndexOutOfBoundsException("position is not in the Board");
        if (height<0 || height>4) throw new IllegalArgumentException("height not valid");
        buildMap[cell[0]][cell[1]] = height;
    }

    /**
     * get Neighbour Cell
     * @author Motta
     * @param cell is a point of the map
     * @return a list of cells of the board adjacent to the input cell
     */
    public List <int[]> getNeighbourCell(int[] cell) {
        List <int[]> res = new ArrayList<int[]>();

        for (int i=-1; i<2; i++){
            for (int j=-1; j<2; j++){
                if (cell[0] +i >=0 && cell[1] +i>=0 && cell[1] + i <=4 && cell[0]+i<=4){
                    int [] temp = new int[2];
                    temp[0] = i;
                    temp[1] = j;
                    if (temp != cell) {
                        res.add(temp);
                    }
                }
            }
        }
        return res;
    }


}
