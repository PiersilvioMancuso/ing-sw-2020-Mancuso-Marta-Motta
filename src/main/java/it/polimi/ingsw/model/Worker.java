package it.polimi.ingsw.model;

        import java.awt.Color;

/**
 * Class to define the worker.
 * @author Mattia Marta.
 */
public class Worker {
    private Color color;

    /**
     * Constructor for the worker.
     * @param color is the color chosen by the user.
     */
    public Worker(Color color) {
        this.color = color;
    }

    /**
     * Color of the worker.
     * @return the color chosen for the worker.
     */
    public Color getColor() {
        return this.color;
    }
}

