package it.polimi.ingsw.model;

/**
 * Class Outcome
 * @author Mattia Marta
 */
public enum OutCome {
    WINNER("You Win!"), LOOSER("You Loose"), DRAW("");
    private final String MESSAGE;

    /**
     * Class constuctor
     * @param message is the message bounded to the condition
     */
    OutCome(String message) {
        this.MESSAGE = message;
    }

    /**
     * Decides if the player has won
     * @param condition is the condition used to check if the player has won
     * @return OutCome of the game if true
     */
    public static OutCome winsIfTrue(boolean condition) {
        if (condition)
            return WINNER;
        else
            return DRAW;
    }

    /**
     * Decides if the player has lost
     * @param condition is the condition used to check if the player has lost
     * @return OutCome of the game if true
     */
    public static OutCome looseIfTrue(boolean condition) {
        if (condition)
            return LOOSER;
        else
            return DRAW;
    }
}
