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

    //Setter

    /**Set the turn state of the player */
    @Override
    public void setStateList(){
        List<State> states = new ArrayList<State>();
        states.add(new BuildState());
        states.add(new MovementState());
        states.add(new BuildState());
        states.add(new EndState());
    }


    /**Set the valid Cells where a player can take the current State action
     * @param modelGame
     * @param worker
     */
    @Override
    public void setValidCells(ModelGame modelGame, Worker worker){
        List<int[]> validPositions = new ArrayList<int[]>();
        int[] workerPosition = modelGame.getWorkerPosition(worker);
        int workerHeight = modelGame.getBoard().getBuildHeight(workerPosition);

        for (int[] position: modelGame.getBoard().getNeighbourCell(workerPosition)){
            int positionHeight = modelGame.getBoard().getBuildHeight(position);
            if (modelGame.getCurrentState() instanceof MovementState){
                if (positionHeight > workerHeight) continue;
            }
            else if (positionHeight > workerHeight + 1 || positionHeight == 4 || modelGame.getWorkerListPosition().contains(position)) continue;
            else validPositions.add(position);
        }

        this.validCells = validPositions;
        AthenaEffectModification(modelGame, worker);
    }

}
