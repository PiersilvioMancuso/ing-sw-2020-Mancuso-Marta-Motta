package it.polimi.ingsw.model;

public class MovementState extends State {

    /**Move a worker to a position in a Cell
     * @author Piersilvio Mancuso
     * @param modelGame is the model of the game
     * @param worker is the worker in use
     * @param position is the position where the worker will be set
     */
    @Override
    public void executeState(ModelGame modelGame, Worker worker, int[] position) {
        modelGame.setWorkerPosition(worker, position);
    }
}
