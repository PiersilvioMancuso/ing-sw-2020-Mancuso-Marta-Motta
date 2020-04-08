package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

/**Athena Power Class
 * @author Piersilvio Mancuso
 */
public class AthenaPower extends Power {

    public AthenaPower(){
        super();
    }

    // ------------- Action ------------------------

    /**Execute the state action and (during movement state) Athena's worker moves up, activate his effect
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the action will take place
     * @exception IllegalArgumentException if position is not a valid cell
     */
    @Override
    public void runPower(ModelGame modelGame, Worker worker, Cell position){
        if (modelGame.getCurrentState() instanceof MovementState || modelGame.getCurrentState() instanceof BuildState){
            if (!validCells.contains(position)) throw new IllegalArgumentException("Position is Invalid");

            int workerAthenaHeight = modelGame.getWorkerPosition(worker).getHeight();

            if (modelGame.getCurrentState() instanceof MovementState){
                setAthenaEffect(false);
            }

            modelGame.getCurrentState().executeState(modelGame, worker, position);

            if (modelGame.getWorkerPosition(worker).getHeight() > workerAthenaHeight) setAthenaEffect(true);
            setNextCurrentState(modelGame);
            setValidCells(modelGame, worker);
        }
    }




}
