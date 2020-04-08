package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;


/**Prometheus Power Class
 * @author Piersilvio Mancuso
 */
public class PrometheusPower extends Power{

    public PrometheusPower(){
        super();
    }

    // ------------ Setter -------------

    /**Set the turn state of the player */
    @Override
    public void setStateList(){
        List<State> states = new ArrayList<>();
        states.add(new BuildState());
        states.add(new MovementState());
        states.add(new BuildState());
        states.add(new EndState());

        this.stateList = states;
    }


    /**Set the valid Cells where a player can take the current State action
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player for its turn
     */
    @Override
    public void setValidCells(ModelGame modelGame, Worker worker){
        List<Cell> validPositions = new ArrayList<>();
        Cell workerPosition = modelGame.getWorkerPosition(worker);
        int workerHeight = workerPosition.getHeight();

        //On Movement State worker cannot move up
        for (Cell position: modelGame.getBoard().getNeighbourCell(workerPosition)){
            int positionHeight = position.getHeight();

            if (modelGame.getCurrentState() instanceof MovementState){
                if (!(positionHeight > workerHeight)) validPositions.add(position);
            }
            else if (modelGame.getCurrentState() instanceof BuildState){
                if (!(positionHeight > workerHeight + 1 || positionHeight == 4 || modelGame.getWorkerListPosition().contains(position))){
                    validPositions.add(position);
                }
            }

        }

        this.validCells = validPositions;
        AthenaEffectModification(modelGame, worker);
    }

}
