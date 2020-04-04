package it.polimi.ingsw.model;

public class SetupState extends State{

    /**User choose where to let the worker start the game in the board
     * @author Piersilvio Mancuso
     * @param modelGame is the base of the game
     * @param worker is the worker who that will be set in position
     * @param position is the position where at the end, the worker will be
     */
    @Override
    public void executeState(ModelGame modelGame, Worker worker, int[] position) {
        modelGame.setWorkerPosition(worker, position);
    }
}
