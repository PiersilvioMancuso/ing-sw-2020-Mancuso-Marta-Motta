package it.polimi.ingsw.model;

public class BuildState extends State {

    /**The worker chosen by his user will build in a position
     * @author Piersilvio Mancuso
     * @param modelGame is the model of the Game
     * @param worker is the worker who will build
     * @param position is the position where the build level will increase
     * @exception NullPointerException if parameters are null
     * @exception IllegalArgumentException if position is not in the board or if worker is trying to build over a Dome
     */
    @Override
    public void executeState(ModelGame modelGame, Worker worker, Cell position) {
        if (modelGame == null || worker == null || position == null) throw new NullPointerException("Parameters cannot be null");
        else if (!modelGame.getBoard().getBuildMap().contains(position)) throw new IllegalArgumentException("Position have to be in the Board");
        else if (position.getHeight() >= 4) throw new IllegalArgumentException("You cannot build over a Dome");
        else {
            position.setHeight(position.getHeight() + 1);
            modelGame.getBoard().setCellBoard(position);
        }

    }
}
