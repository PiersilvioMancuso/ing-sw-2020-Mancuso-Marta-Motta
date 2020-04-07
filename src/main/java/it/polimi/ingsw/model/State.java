package it.polimi.ingsw.model;

abstract public class State {

    /**Execute the proper action for the State
     * @author Piersilvio Mancuso
     * @param modelGame is the model of the game
     * @param worker is the worker who will execute the action
     * @param position is the position where the action will be acted
     */
    public abstract void executeState(ModelGame modelGame, Worker worker, Cell position);
}
