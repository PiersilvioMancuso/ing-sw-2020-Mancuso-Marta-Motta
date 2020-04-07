package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.Power;

abstract public class God{
    protected Power power;

    public Power getPower() {
        return power;
    }

    public abstract void activatePower(ModelGame modelGame, Worker worker);

    public abstract void setUpTurn(ModelGame modelGame, Worker worker);

    public abstract boolean isLoser(ModelGame modelGame, Worker worker);

    public abstract void executePower(ModelGame modelGame, Worker worker, Cell position);

}
