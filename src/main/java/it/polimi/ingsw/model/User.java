package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;

import java.awt.*;
import java.util.concurrent.locks.Condition;

/**
 * Class User.
 * @author Mattia Marta.
 */
public class User {
    private String username;
    private Color color;
    private God godChosen;

    /**
     * Constructor of the user.
     * @param username is the nickname assigned to a user.
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Username of the player.
     * @return current username of the player.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Username of the player.
     * @param username is the new value for this player's usenrname.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Color chosen by the player.
     * @return the color chosen.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Color chosen by the player.
     * @param color is the color chosen by the player.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * God chosen by the player.
     * @return the name of the chosen God
     */
    public God getGod() {
        return this.godChosen;
    }

    /**
     * God chosen by the player.
     * @param god is the name of the chosen God.
     */
    public void setGod(God god) {
        this.godChosen = god;
    }

}
