package it.polimi.ingsw.controller.controllerState;

import it.polimi.ingsw.controller.action.*;

/**Players In Game Choice State
 * @author Piersilvio Mancuso
 */
public class PlayersInGameChoiceControllerState extends ControllerState{


    // ------------------ CONSTRUCTOR ---------------

    /** PlayersInGameChoiceControllerState Constructor
     */
    public PlayersInGameChoiceControllerState() {
        this.className = "PlayersInGameChoiceControllerState";
    }

    /**Create the Action of the State
     * @param string is a string used for PlayerInGameChoiceAction creation
     * @return a PlayerInGameChoiceAction object
     */
    @Override
    public Action createAction(String string) {
        return new PlayersInGameChoiceAction(string);
    }
}