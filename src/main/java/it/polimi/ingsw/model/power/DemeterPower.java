package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;

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
        List<State> states = new ArrayList<>();
        states.add(new MovementState());
        states.add(new BuildState());
        states.add(new BuildState());
        states.add(new EndState());

        this.stateList = states;
    }


    //Action


    /**Initialize the Power
     *
     * @param modelGame is the model of the Game
     * @param worker is the worker that will be used by the player
     */
    public void startPower(ModelGame modelGame, Worker worker){
        setStateList();
        setValidCells(modelGame, worker);

        if (isAthenaEffect() && modelGame.getCurrentState() instanceof MovementState){
            int workerHeight = modelGame.getWorkerPosition(worker).getHeight();

            for (Cell position : getValidCells()){
                int positionHeight = position.getHeight();
                if (positionHeight > workerHeight) validCells.remove(position);
            }
        }
    }

    /**Execute the state action and if it is in the second Build State it will remove the first position built
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the action will take place
     * @exception IllegalArgumentException if position is not a valid cell
     */
    public void runPower(ModelGame modelGame, Worker worker, Cell position){
        if (modelGame.getCurrentState() instanceof MovementState || modelGame.getCurrentState() instanceof BuildState){
            if (!validCells.contains(position)) throw new IllegalArgumentException("Position is Invalid");

            modelGame.getCurrentState().executeState(modelGame, worker, position);

            if (modelGame.getCurrentState() instanceof MovementState){
                setNextCurrentState(modelGame);
                setValidCells(modelGame, worker);
            }

            else if (modelGame.getCurrentState() instanceof BuildState){
                setNextCurrentState(modelGame);
                setValidCells(modelGame, worker);
                if (modelGame.getCurrentState() instanceof BuildState) this.validCells.remove(position);
            }

        }
    }


}
