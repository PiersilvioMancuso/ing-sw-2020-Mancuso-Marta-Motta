package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.*;


public class Athena extends God {

    /**Create the God Athena which use Athena Power */
    public Athena(){
        this.power = new AthenaPower();
        this.power.setActiveEffect(true);
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


    /**SetUp worker's turn with activated effect
     * @param modelGame is the model of the game
     * @param worker is the worker that will be used
     */
    @Override
    public void setUpTurn(ModelGame modelGame, Worker worker) {
        power.setActiveEffect(true);
        super.setUpTurn(modelGame, worker);
    }




}
