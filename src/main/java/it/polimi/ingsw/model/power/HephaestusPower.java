package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.state.BuildState;
import it.polimi.ingsw.model.state.MovementState;

/**Hephaestus Power Class
 * @author Piersilvio Mancuso
 */
public class HephaestusPower extends Power{


    //Action

    /**Execute the state action and if Hephaestus Power is ON, during Build State he can build another level in the same position, but not a Dome
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the action will take place
     * @exception IllegalArgumentException if position is not a valid cell
     */
    @Override
    public void runPower(ModelGame modelGame, Worker worker, Cell position) throws IllegalArgumentException{
        if (!isActiveEffect()) super.runPower(modelGame, worker, position);

        else {
            if (!(modelGame.getCurrentState() instanceof BuildState)) super.runPower(modelGame, worker, position);

            else {
                if (!validCells.contains(position)) throw new IllegalArgumentException("Position is Not a Valid Cell");

                modelGame.getCurrentState().executeState(modelGame, worker, position);
                if (position.getHeight() <3) modelGame.getCurrentState().executeState(modelGame, worker, position);
                setNextCurrentState(modelGame);
                setValidCells(modelGame, worker);
            }
        }

    }


}
