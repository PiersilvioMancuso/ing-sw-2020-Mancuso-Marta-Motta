package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

/**Atlas Power Class
 * @author Piersilvio Mancuso
 */
public class AtlasPower extends Power{

    public AtlasPower(){
        super();
    }



    /**Execute the state action and, if the game is in the Build State, the worker will build in the same cell until in position there is a Dome.
     *
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the action will be acted
     */
    @Override
    public void runPower(ModelGame modelGame, Worker worker, Cell position){
        if (modelGame.getCurrentState() instanceof MovementState){
            modelGame.getCurrentState().executeState(modelGame, worker, position);
            setNextCurrentState(modelGame);
            setValidCells(modelGame, worker);
        }

        else if (modelGame.getCurrentState() instanceof BuildState){
            while (position.getHeight() < 4){
                modelGame.getCurrentState().executeState(modelGame, worker, position);
            }

            setNextCurrentState(modelGame);
            setValidCells(modelGame, worker);
        }
    }

}
