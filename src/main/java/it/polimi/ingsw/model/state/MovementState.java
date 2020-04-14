package it.polimi.ingsw.model.state;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.Worker;

public class MovementState extends State {

    /**Move a worker to a position in a Cell
     * @author Piersilvio Mancuso
     * @param modelGame is the model of the game
     * @param worker is the worker in use
     * @param position is the position where the worker will be set
     */
    @Override
    public void executeState(ModelGame modelGame, Worker worker, Cell position) {

        if (modelGame == null || worker == null || position == null) throw new NullPointerException("Parameters cannot be null");
        else if (modelGame.getWorkerListPosition().contains(position)) throw new IllegalArgumentException("Position is already occupied");
        else modelGame.setWorkerPosition(worker, position);

    }
}
