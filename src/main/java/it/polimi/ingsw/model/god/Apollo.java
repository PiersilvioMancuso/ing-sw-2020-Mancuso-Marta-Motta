package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.ApolloPower;
import it.polimi.ingsw.model.power.Power;

/**Apollo God Class
 * @author Piersilvio Mancuso
 */
public class Apollo extends God {

    /**Create the God Apollo which use Apollo Power */
    public Apollo(){
        this.power = new ApolloPower();
    }

    /**Activate the power on a Worker
     *
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     */
    @Override
    public void activatePower(ModelGame modelGame, Worker worker) {
        power.setActiveEffect(true);
    }



}
