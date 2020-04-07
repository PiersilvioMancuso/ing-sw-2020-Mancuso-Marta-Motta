package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

/**Artemis Power Class
 * @author Piersilvio Mancuso
 */
public class ArtemisPower extends Power {

    public ArtemisPower(){
        super();
    }


    // ------------- Setter ---------------------

    /**Set the turn state of the player */
    @Override
    public void setStateList(){
        List<State> states = new ArrayList<>();
        states.add(new MovementState());
        states.add(new MovementState());
        states.add(new BuildState());
        states.add(new EndState());
    }


    // ------------ Action -------------------

    /**Execute the state action and remove in the second movement the first worker position
     *
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the action will be acted
     */
    @Override
    public void runPower(ModelGame modelGame, Worker worker, Cell position){
        if (modelGame.getCurrentState() instanceof MovementState || modelGame.getCurrentState() instanceof BuildState){
            Cell workerPosition = modelGame.getWorkerPosition(worker);
            modelGame.getCurrentState().executeState(modelGame, worker, position);

            setNextCurrentState(modelGame);
            setValidCells(modelGame, worker);

            if (modelGame.getCurrentState() instanceof MovementState){
                this.validCells.remove(workerPosition);
            }
        }
    }


}
