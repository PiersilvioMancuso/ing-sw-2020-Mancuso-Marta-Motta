package it.polimi.ingsw.model.state;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.messages.modelViewMessages.ModelUpdate;

/**SetupState Class
 * @author Piers
 */
public class SetupState extends State{

    /**User choose where to let the worker start the game in the board
     * @author Piersilvio Mancuso
     * @param modelGame is the base of the game
     * @param worker is the worker who that will be set in position
     * @param position is the position where at the end, the worker will be
     */
    @Override
    public void executeState(ModelGame modelGame, Worker worker, Cell position) {
        if (modelGame == null || worker == null || position == null) throw new NullPointerException("Parameters cannot be null");
        else if (modelGame.getWorkerListPosition().contains(position)) throw new IllegalArgumentException("Position is already occupied");
        else {
            modelGame.setWorkerPosition(worker, position);
        }
        modelGame.addUpdate(new ModelUpdate(modelGame));

    }
}
