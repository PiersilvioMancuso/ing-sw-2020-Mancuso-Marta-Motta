package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.*;


/**Pan God Class
 * @author Piersilvio Mancuso
 */
public class Pan extends God {

    // ---------------- CONSTRUCTOR ----------------

    /**Create the God Pan which use Pan Power */
    public Pan(){
        this.power = new PanPower();
        power.setActiveEffect(true);
    }


    // ----------------- SETTER -------------------

    /**SetUp worker's turn with activated effect
     * @param modelGame is the model of the game
     * @param worker is the worker that will be used
     */
    @Override
    public void setUpTurn(ModelGame modelGame, Worker worker) {
        power.setActiveEffect(true);
        super.setUpTurn(modelGame, worker);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":\t" + power;
    }
}
