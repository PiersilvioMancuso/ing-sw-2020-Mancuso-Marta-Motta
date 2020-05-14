package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.state.BuildState;
import it.polimi.ingsw.model.state.MovementState;

/**Athena Power Class
 * @author Piersilvio Mancuso
 */
public class AthenaPower extends Power {

    public AthenaPower(){
        super();
        this.textEffect = "If one of your Workers moved up on your last\n" + "\t\tturn, opponent Workers cannot move up this turn.";
    }

    // ------------- Action ------------------------

    /**Execute the state action and (during movement state) Athena's worker moves up, activate his effect
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the action will take place
     * @exception IllegalArgumentException if position is not a valid cell
     */
    @Override
    public void runPower(ModelGame modelGame, Worker worker, Cell position) throws IllegalArgumentException{
        if (modelGame.getCurrentState() instanceof MovementState) setAthenaEffect(false);
        int workerHeight = worker.getPosition().getHeight();
        super.runPower(modelGame, worker, position);

        if (worker.getPosition().getHeight() > workerHeight) setAthenaEffect(true);
    }




}
