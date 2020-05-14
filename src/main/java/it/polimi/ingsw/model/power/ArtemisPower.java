package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.state.BuildState;
import it.polimi.ingsw.model.state.EndState;
import it.polimi.ingsw.model.state.MovementState;
import it.polimi.ingsw.model.state.State;

import java.util.ArrayList;
import java.util.List;

/**Artemis Power Class
 * @author Piersilvio Mancuso
 */
public class ArtemisPower extends Power {

    public ArtemisPower(){
        super();
        this.textEffect = "Your Worker may move one additional time, but not\n" + "\t\t\tback to its initial space.";
    }


    // ------------- Setter ---------------------

    /**Set the turn state of the player */
    @Override
    public void setStateList(){
        super.setStateList();
        if (isActiveEffect()) stateList.add(0, new MovementState());
    }


    // ------------ Action -------------------

    /**Execute the state action and remove in the second movement the first worker position
     *
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the action will be acted
     */
    @Override
    public void runPower(ModelGame modelGame, Worker worker, Cell position) throws IllegalArgumentException{
        if (!isActiveEffect()) super.runPower(modelGame, worker, position);

        else {
            Cell workerPosition = worker.getPosition();
            super.runPower(modelGame, worker, position);

            if (modelGame.getCurrentState() instanceof MovementState) modelGame.getValidCells().remove(workerPosition);
        }

    }


}
