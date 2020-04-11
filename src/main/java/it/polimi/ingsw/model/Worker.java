package it.polimi.ingsw.model;

        import java.awt.Color;

/**
 * Class to define the worker.
 * @author Mattia Marta.
 */
public class Worker {
    private User user;
    private Color color;

    /**
     * Constructor for the worker.
     * @param user is the user who controls the worker
     */
    public Worker(User user) {
        this.user = user;
        this.color = user.getColor();
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
}

