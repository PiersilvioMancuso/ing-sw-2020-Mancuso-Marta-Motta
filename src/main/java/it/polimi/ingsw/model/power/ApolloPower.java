package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.Athena;

import java.util.ArrayList;
import java.util.List;

/**Apollo Power Class
 * @author Piersilvio Mancuso
 */
public class ApolloPower extends Power{

    public ApolloPower(){
        super();
    }


    // --------------- Setter ------------------------


    /**Set the valid Cells where a player can take the current State action
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player in game
     */
    @Override
    public void setValidCells(ModelGame modelGame, Worker worker){
        List<int[]> validPositions = new ArrayList<int[]>();
        int[] workerPosition = modelGame.getWorkerPosition(worker);
        int workerHeight = modelGame.getBoard().getBuildHeight(workerPosition);

        for (int[] position: modelGame.getBoard().getNeighbourCell(workerPosition)){
            int positionHeight = modelGame.getBoard().getBuildHeight(position);
            if (positionHeight > workerHeight + 1 || positionHeight == 4 || modelGame.getWorkerListPosition().contains(position)) continue;
            else validPositions.add(position);
        }
        if (modelGame.getCurrentState() instanceof MovementState){
            User userWorker = worker.getUser();
            for (int i = 0; i < modelGame.getWorkerListPosition().size(); i++){
                int[] position = modelGame.getWorkerListPosition().get(i);
                if (modelGame.getWorkerList().get(i).getUser().equals(userWorker) && modelGame.getBoard().getNeighbourCell(modelGame.getWorkerPosition(worker)).contains(position)){
                    validPositions.add(position);
                }
            }
        }
        this.validCells = validPositions;
        AthenaEffectModification(modelGame, worker);
    }


    // ------------ Action -------------------

    /**Execute the state action
     *
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the action will be acted
     */
    public void runPower(ModelGame modelGame, Worker worker, int[] position){
        if (modelGame.getCurrentState() instanceof MovementState || modelGame.getCurrentState() instanceof BuildState){

            if (modelGame.getWorkerListPosition().contains(position)){
                int[] workerPosition = modelGame.getWorkerPosition(worker);
                Worker otherWorker = modelGame.getWorkerList().get(modelGame.getWorkerListPosition().indexOf(position));
                modelGame.setWorkerPosition(otherWorker, workerPosition);
            }

            modelGame.getCurrentState().executeState(modelGame, worker, position);

            setNextCurrentState(modelGame);
            setValidCells(modelGame, worker);
        }
    }


}
