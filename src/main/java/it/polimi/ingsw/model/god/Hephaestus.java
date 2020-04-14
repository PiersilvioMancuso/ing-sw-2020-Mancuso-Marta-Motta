package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.*;

public class Hephaestus extends God {


    /**Create the God Hephaestus which use Hephaestus Power */
    public Hephaestus(){
        this.power = new HephaestusPower();
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
