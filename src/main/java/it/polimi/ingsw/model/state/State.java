package it.polimi.ingsw.model.state;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.Worker;

import java.io.Serializable;

abstract public class State implements Serializable {

    /**Execute the proper action for the State
     * @author Piersilvio Mancuso
     * @param modelGame is the model of the game
     * @param worker is the worker who will execute the action
     * @param position is the position where the action will be acted
     */
    public abstract void executeState(ModelGame modelGame, Worker worker, Cell position);
}
