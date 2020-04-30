package it.polimi.ingsw.view;

/**Use to enum command and use by run method to compare the commands sent by the
 * controller and those of the view to call the right methods
 *
 * @author Veronica Motta
 */
public enum Command {
    REGISTER("register"),
    PLAYERS("numberPlayers"),
    COLOR("color"),
    GOD_LIST_THREE("GodListThree"),
    GOD_LIST_TWO("GodListTwo"),
    GOD("god"),
    SET_WORKER_POSITION("setWorkerPosition"),
    USE_GOD_POWER("useGodPower"),
    MOVE("move"),
    BUILD("build"),
    LOOSE("loose"),
    WIN("win"),
    QUIT("quit");

    private final String code;


    Command(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }


    }
