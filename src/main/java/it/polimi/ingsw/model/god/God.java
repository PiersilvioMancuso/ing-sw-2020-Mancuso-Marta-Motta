package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.Power;

abstract public class God{
    protected Power power;

    public Power getPower() {
        return power;
    }

    abstract void activatePower(ModelGame modelGame, Worker worker);

    abstract void setUpTurn(ModelGame modelGame, Worker worker);

    abstract boolean isLoser(ModelGame modelGame, Worker worker);

    abstract void executePower(ModelGame modelGame, Worker worker, int[] position);

}
