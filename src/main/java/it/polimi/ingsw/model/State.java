package it.polimi.ingsw.model;

abstract public class State {

    /**Execute the proper action for the State
     * @author Piersilvio Mancuso
     * @param modelGame
     * @param worker
     * @param position
     */
    public abstract void executeState(ModelGame modelGame, Worker worker, int[] position);
}
