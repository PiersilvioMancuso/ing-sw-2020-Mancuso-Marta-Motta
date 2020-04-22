package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.action.*;

/**Players In Game Choice State
 * @author Piersilvio Mancuso
 */
public class PlayersInGameChoiceState extends ControllerState{


    /**Create the Action of the State
     * @param string is a string used for PlayerInGameChoiceAction creation
     * @return a PlayerInGameChoiceAction object
     */
    @Override
    public Action createAction(String string) {
        return new PlayersInGameChoiceAction(string);
    }
}
