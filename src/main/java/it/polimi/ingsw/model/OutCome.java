package it.polimi.ingsw.model;

public enum OutCome {
    WINNER("You Win!"), LOOSER("You Loose");
    private final String MESSAGE;

    OutCome(String message) {
        this.MESSAGE = message;
    }

    public OutCome winsIfTrue(boolean condition) {
        return null;
    }

    public OutCome looseIfTrue(boolean condition) {
        return null;
    }
}
