package it.polimi.ingsw.model;

import java.io.Serializable;

public enum ModelColor implements Serializable {
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


    public String getMessage() {
        return message;
    }
}
