package it.polimi.ingsw.model;

public enum ModelColor {
    BLUE("Blue"),
    RED("Red"),
    CYAN("Cyan"),
    YELLOW("Yellow"),
    GREEN("Green"),
    PURPLE("Purple");

    private final String message;


    ModelColor(String message) {
        this.message = message;
    }

}
