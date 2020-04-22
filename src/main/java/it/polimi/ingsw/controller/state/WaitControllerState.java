package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.action.Action;

/**Wait Controller State Class
 * @author Piersilvio Mancuso
 */
public class WaitControllerState extends ControllerState{

    /**It has no effect
     * @param string that will not be used
     * @return null
     */
    @Override
    public Action createAction(String string) {
        return null;
    }

}
