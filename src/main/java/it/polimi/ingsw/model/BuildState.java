package it.polimi.ingsw.model;

public class BuildState extends State {

    /**The worker chosen by his user will build in a position
     * @author Piersilvio Mancuso
     * @param modelGame is the model of the Game
     * @param worker is the worker who will build
     * @param position is the position where the build level will increase
     */
    @Override
    public void executeState(ModelGame modelGame, Worker worker, int[] position) {
        modelGame.getBoard().setBuildHeight(position, modelGame.getBoard().getBuildHeight(position) + 1);
    }
}
