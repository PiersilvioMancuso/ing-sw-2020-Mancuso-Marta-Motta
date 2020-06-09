package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.*;

/**Athena God Class
 * @author Piersilvio Mancuso
 */
public class Athena extends God {

    // ------------------ CONSTRUCTOR ----------------

    /**Create the God Athena which use Athena Power */
    public Athena(){
        this.power = new AthenaPower();
        this.power.setActiveEffect(true);
    }

    /**SetUp worker's turn with activated effect
     * @param modelGame is the model of the game
     * @param worker is the worker that will be used
     */
    @Override
    public void setUpTurn(ModelGame modelGame, Worker worker) {
        power.setActiveEffect(false);
        power.setActiveEffect(true);
        super.setUpTurn(modelGame, worker);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":\t" + power;
    }


}
