package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.Power;

abstract public class God{
    protected Power power;

    public Power getPower() {
        return power;
    }

    public abstract void activatePower(ModelGame modelGame, Worker worker);


    /**SetUp worker's turn
     *
     * @param modelGame is the model of the game
     * @param worker is the worker that will be used
     */
    public void setUpTurn(ModelGame modelGame, Worker worker) {
        power.startPower(modelGame, worker);
    }


    /**Check if the player, who use the worker, loose the game
     *
     * @param modelGame is the model of the game
     * @param worker is the worker chosen by the player
     * @return true if the player loose, otherwise false
     */
    public boolean isLoser(ModelGame modelGame, Worker worker) {
        return power.getValidCells().size() == 0;
    }


    /**Execute the state of the game and, if the player wins, set the outcome of all players
     *  @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the the action will take place using the worker
     * @exception IllegalArgumentException if position is not a valid cell
     */
    public void executePower(ModelGame modelGame, Worker worker, Cell position) {
        if (!power.getValidCells().contains(position)) throw new IllegalArgumentException("Position is Invalid");
        power.runPower(modelGame, worker, position);

    }
}
