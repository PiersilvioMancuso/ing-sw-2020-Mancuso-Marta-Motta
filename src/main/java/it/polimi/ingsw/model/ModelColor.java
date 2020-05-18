package it.polimi.ingsw.model;

import java.io.Serializable;

/**ModelColor Enumeration
 * @author Piersilvio Mancuso
 */
public enum ModelColor implements Serializable {
    BLUE("Blue"),
    RED("Red"),
    CYAN("Cyan"),
    YELLOW("Yellow"),
    GREEN("Green"),
    PURPLE("Purple");

    private final String message;

    /**ModelColor Constructor
     * @param message is the message for the enumeration
     */
    ModelColor(String message) {
        this.message = message;
    }

    /**Message Getter
     * @return the message for enumeration Constant
     */
    public String getMessage() {
        return message;
    }
}
