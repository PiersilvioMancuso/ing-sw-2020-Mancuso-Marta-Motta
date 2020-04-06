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
        //nessun worker nella stessa posizione
        if (modelGame == null) throw new IllegalArgumentException("modelGame is null");
        if (worker == null) throw new IllegalArgumentException("worker is null");
        if (position == null) throw new IllegalArgumentException("position is null");
        if (modelGame.getWorkerListPosition().contains(position)) throw new IllegalArgumentException("position is occupied");
        modelGame.setWorkerPosition(worker, position);
    }
}
