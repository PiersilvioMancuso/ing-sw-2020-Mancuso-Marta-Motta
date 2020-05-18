package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;

import java.io.Serializable;

/**
 * Class User.
 * @author Mattia Marta.
 */
public class User implements Serializable {
    private String username;
    private ModelColor color;
    private God godChosen;
    private OutCome outCome;
    private int age;


    public User(){

    }

    /**
     * Constructor of the user.
     * @param username is the nickname assigned to a user.
     */
    public User(String username) {
        this.outCome = OutCome.DRAW;
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
    public ModelColor getColor() {
        return this.color;
    }

    /**
     * Color chosen by the player.
     * @param color is the color chosen by the player.
     */
    public void setColor(ModelColor color) {
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

    /**
     * Get the outCome parameter
     * @return the outcome of the game for the user
     */
    public OutCome getOutCome() {
        return outCome;
    }

    /**
     * Set the outCome parameter
     * @param outCome is the result of the game for the user
     */
    public void setOutCome(OutCome outCome) {
        this.outCome = outCome;
    }

    /**
     * Get the age of the person
     * @return the age of the user
     */
    public int getAge() {
        return age;
    }

    /**
     * Set the age of the player
     * @param age is the age of the user
     */
    public void setAge(int age) {
        this.age = age;
    }


    /**User's toString
     * @return a String that denote how an User can be printed*/
    @Override
    public String toString() {
        if (godChosen!= null){
            return username + ":\t" + godChosen.getClass().getSimpleName() + " - " + outCome;
        }
        else if(color != null) {
            return username + ":\t" +  godChosen.getClass().getSimpleName() + " - " + color +  " - " + outCome;
        }
        else {
            return username + ":\t" + outCome;
        }

    }
}
