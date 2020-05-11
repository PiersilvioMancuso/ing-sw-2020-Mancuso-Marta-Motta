package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.state.BuildState;
import it.polimi.ingsw.model.state.EndState;
import it.polimi.ingsw.model.state.MovementState;
import it.polimi.ingsw.model.state.State;

import java.util.ArrayList;
import java.util.List;


/**Demeter Power Class
 * @author Piersilvio Mancuso
 */
public class DemeterPower extends Power{

    public DemeterPower(){
        super();
    }

    /**Set the turn state of the player */
    public void setStateList(){
        super.setStateList();
        if (isActiveEffect()) stateList.add(1, new BuildState());
    }


    // ------------------- Action -------------------


    /**Execute the state action and if it is in the second Build State it will remove the first position built
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the action will take place
     * @exception IllegalArgumentException if position is not a valid cell
     */
    public void runPower(ModelGame modelGame, Worker worker, Cell position) throws IllegalArgumentException{

        if (!isActiveEffect()) super.runPower(modelGame, worker, position);

        else{
            State startState = modelGame.getCurrentState();
            super.runPower(modelGame, worker, position);
            if (startState instanceof BuildState && modelGame.getCurrentState() instanceof BuildState){
                modelGame.getValidCells().remove(position);
            }
        }

    }


}
