package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

/**Hephaestus Power Class
 * @author Piersilvio Mancuso
 */
public class HephaestusPower extends Power{


    //Action

    /**Execute the state action and if Hephaestus Power is ON, during Build State he can build another level in the same position, but not a Dome
     *
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the action will be acted
     */
    @Override
    public void runPower(ModelGame modelGame, Worker worker, Cell position){
        if (modelGame.getCurrentState() instanceof MovementState || modelGame.getCurrentState() instanceof BuildState){
            modelGame.getCurrentState().executeState(modelGame, worker, position);

            if (position.getHeight() < 3 && modelGame.getCurrentState() instanceof BuildState){
                modelGame.getCurrentState().executeState(modelGame, worker, position);
            }

            setNextCurrentState(modelGame);
            setValidCells(modelGame, worker);
        }
    }


}
