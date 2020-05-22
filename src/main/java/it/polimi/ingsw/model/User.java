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



    // ---------------- CONSTRUCTOR --------------

    /**User void Constructor
     */
    public User(){
        this.outCome = OutCome.DRAW;
    }

    /**
     * Constructor of the user.
     * @param username is the nickname assigned to a user.
     */
    public User(String username) {
        this.outCome = OutCome.DRAW;
        this.username = username;
    }



    // ----------------- GETTER --------------------

    /**GodChosen Setter
     * @return the god chosen by the User
     */
    public God getGodChosen() {
        return godChosen;
    }

    /**
     * Username of the player.
     * @return current username of the player.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * God chosen by the player.
     * @return the name of the chosen God
     */
    public God getGod() {
        return this.godChosen;
    }


    /**
     * Get the outCome parameter
     * @return the outcome of the game for the user
     */
    public OutCome getOutCome() {
        return outCome;
    }

    /**
     * Color chosen by the player.
     * @return the color chosen.
     */
    public ModelColor getColor() {
        return this.color;
    }



    // ----------------- SETTER --------------------

    /**Color Setter
     * @param color is the color chosen by the player.
     */
    public void setColor(ModelColor color) {
        this.color = color;
    }


    /**GodSetter
     * @param god is the name of the chosen God.
     */
    public void setGodChosen(God god) {
        this.godChosen = god;
    }



    /**Set the outCome parameter
     * @param outCome is the result of the game for the user
     */
    public void setOutCome(OutCome outCome) {
        this.outCome = outCome;
    }


    /**Get the age of the person
     * @return the age of the user
     */
    public int getAge() {
        return age;
    }


    /** Set the age of the player
     * @param age is the age of the user
     */
    public void setAge(int age) {
        this.age = age;
    }



    /**
     * Username of the player.
     * @param username is the new value for this player's usenrname.
     */
    public void setUsername(String username) {
        this.username = username;
    }



    // ---------------- STRING ------------------


    /**User's toString
     * @return a String that denote how an User can be printed*/
    @Override
    public String toString() {
        String printed ;
        if (godChosen!= null){
            printed = username + ":\t" + godChosen.getClass().getSimpleName() + " - " + outCome;
        }
        else if(color != null) {
            printed = username + ":\t" +  godChosen.getClass().getSimpleName() + " - " + color +  " - " + outCome;
        }
        else {
            printed = username + ":\t" + outCome;
        }

        return printed;
    }
}
