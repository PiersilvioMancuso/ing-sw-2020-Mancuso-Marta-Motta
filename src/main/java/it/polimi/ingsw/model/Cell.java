package it.polimi.ingsw.model;

import java.util.Objects;

/**
 * Class that manage all the parameters of a cell element.
 * @author Mattia Marta
 */
public class Cell {
    private int x;
    private int y;
    private int height;


    public Cell (){
        this.x = 0;
        this.y = 0;
        this.height = 0;
    }

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.height = 0;
    }

    public Cell(int x, int y, int height){
        this.x = x;
        this.y = y;
        this.height = height;
    }

    public Cell (Cell cell){
        this.x = cell.getX();
        this.y = cell.getY();
        this.height = cell.getHeight();
    }


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
        return getX() == cell.getX() &&
                getY() == cell.getY();
    }


    /**
     * Getter for the x parameter
     * @return value of x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for the y parameter
     * @return value of y
     */
    public int getY() {
        return y;
    }

    /**
     * Getter for the height parameter
     * @return height of the cell
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter for the x parameter
     * @param x is the first compoment of position (x,y)
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter for the y parameter
     * @param y is the second compoment of position (x,y)
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Setter for the height parameter
     * @param height is the level of the cell
     */
    public void setHeight(int height) {
        this.height = height;
    }



}
