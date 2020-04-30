package it.polimi.ingsw.view;

/**Use to enum the color that the user can choose and the color used by the Cli to print the board and the String
 *
 * @author Veronica Motta
 */
public enum CliColor {
    TITLE("\u001b[36m"),
    RESET("\u001b[0m"),
    BLACK("\u001b[30m"),
    WHITE("\\u001b[37m"),

    //Color that a user can choose
    RED("\u001b[31m"),
    GREEN("\u001b[32m"),
    YELLOW("\u001b[33m"),
    BLUE("\u001b[34m"),
    PURPLE("\u001b[35m"),
    CYAN("\u001b[36m"),


    //Background's color
    RED_BACKGROUND("\u001b[41m"),
    GREEN_BACKGROUND("\u001b[42m"),
    YELLOW_BACKGROUND("\u001b[43m"),
    BLUE_BACKGROUND("\u001b[44m"),
    PURPLE_BACKGROUND("\u001b[45m"),
    CYAN_BACKGROUND("\u001b[46m"),
    BLACK_BACKGROUND("\u001b[40m"),
    WHITE_BACKGROUND("\u001b[47m"),

    RED_BACKGROUND_BRIGHT("\u001b[41m;1m"),
    GREEN_BACKGROUND_BRIGHT("\u001b[42m;1m"),
    YELLOW_BACKGROUND_BRIGHT("\u001b[43m;1m"),
    BLUE_BACKGROUND_BRIGHT("\u001b[44m;1m"),
    PURPLE_BACKGROUND_BRIGHT("\u001b[45m;1m"),
    CYAN_BACKGROUND_BRIGHT("\u001b[46m;1m"),
    BLACK_BACKGROUND_BRIGHT("\u001b[40m;1m"),
    WHITE_BACKGROUND_BRIGHT("\u001b[47m;1m");



    private final String code;

    CliColor(String code) {this.code= code;
    }

    @Override
    public String toString() {return code;
    }
}
