package it.polimi.ingsw.model;

        import java.awt.Color;

/**
 * Class to define the worker.
 * @author Mattia Marta.
 */
public class Worker {
    private User user;
    private Color color;
    private Cell position;

    /**
     * Constructor for the worker.
     * @param user is the user who controls the worker
     */
    public Worker(User user) {
        this.user = user;
        this.color = user.getColor();
        this.position = new Cell(-1,-1);
    }

    /**
     * User who controls the worker
     * @return the user object of the worker's owner
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the User to the worker
     * @param user is the User to set to the worker
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Color of the worker.
     * @return the color chosen for the worker.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Set a color to the worker
     * @param color is the color chosen for the worker
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**Get the position of the worker
     * @return  the Cell where the worker is
     */
    public Cell getPosition() {
        return position;
    }

    /**Set the positon of the Worker
     * @param position is the Cell where the worker will be
     */
    public void setPosition(Cell position) {
        this.position = position;
    }
}

