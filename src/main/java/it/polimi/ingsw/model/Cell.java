package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class that manage all the parameters of a cell element.
 * @author Mattia Marta and Piersilvio Mancuso
 */
public class Cell implements Serializable {
    private int row;
    private int column;
    private int height;

    // ------------------- CONSTRUCTOR -----------------

    /**Cell void Constructor*/
    public Cell (){
        this.row = 0;
        this.column = 0;
        this.height = 0;
    }

    /**Cell Constructor with Position
     * @param row is the Column of the Cell Created
     * @param column is the Row of the Cell Created
     */
    public Cell(int row, int column){
        this.row = row;
        this.column = column;
        this.height = 0;
    }

    /**Cell Constructor with Position and Height
     * @param row is the Column of the Cell Created
     * @param column is the Row of the Cell Created
     * @param height  is the height that will be created
     */
    public Cell(int row, int column, int height){
        this.row = row;
        this.column = column;
        this.height = height;
    }

    /**Cell Copy Constructor
     * @param cell is the cell that will be copied
     */
    public Cell (Cell cell){
        this.row = cell.getRow();
        this.column = cell.getColumn();
        this.height = cell.getHeight();
    }


    // ----------------- GETTER ---------------------

    /**
     * Getter for the x parameter
     * @return value of x
     */
    public int getRow() {
        return row;
    }


    /**
     * Getter for the y parameter
     * @return value of y
     */
    public int getColumn() {
        return column;
    }

    /**
     * Getter for the height parameter
     * @return height of the cell
     */
    public int getHeight() {
        return height;
    }




    // ----------------- SETTER ---------------------


    /**
     * Setter for the x parameter
     * @param row is the first compoment of position (x,y)
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Setter for the y parameter
     * @param column is the second compoment of position (x,y)
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Setter for the height parameter
     * @param height is the level of the cell
     */
    public void setHeight(int height) {
        this.height = height;
    }


    // ----------------- EQUALS --------------------

    /**
     * This method is used to override equals in "contains()" method
     * @param o is the object passed to the function, expected a cell
     * @return is a bool, true if equals, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return getRow() == cell.getRow() &&
                getColumn() == cell.getColumn();
    }




}
