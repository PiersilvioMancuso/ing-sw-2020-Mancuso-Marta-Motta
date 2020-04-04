package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import java.util.ArrayList;
import java.util.List;

/**Minotaur Power Class
 * @author Piersilvio Mancuso
 */
public class MinotaurPower extends Power{

    public MinotaurPower(){
        super();
    }

    //Setter

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
            if (positionHeight > workerHeight + 1 || positionHeight == 4 || modelGame.getWorkerListPosition().contains(position)) continue;
            else validPositions.add(position);
        }

        if (modelGame.getCurrentState() instanceof MovementState){
            for (int i = 0; i < modelGame.getValidNeighbour(workerPosition).size(); i++){
                int[] position = modelGame.getWorkerListPosition().get(i);
                if (modelGame.getWorkerListPosition().contains(position)){
                    int[] lastPosition = new int[2];
                    lastPosition[0] = 2 * position[0] - workerPosition[0];
                    lastPosition[1] = 2 * position[1] - workerPosition[1];

                    if (modelGame.getBoard().getNeighbourCell(position).contains(lastPosition) && modelGame.getBoard().getBuildHeight(lastPosition) < 4 && !modelGame.getWorkerListPosition().contains(lastPosition)){
                        if (!validPositions.contains(position)) validPositions.add(position);
                    }
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
    @Override
    public void runPower(ModelGame modelGame, Worker worker, int[] position){
        if (modelGame.getCurrentState() instanceof MovementState || modelGame.getCurrentState() instanceof BuildState){
            if (modelGame.getCurrentState() instanceof MovementState){
                if (modelGame.getWorkerListPosition().contains(position)){
                    int[] lastPosition = new int[2];
                    int[] workerPosition = modelGame.getWorkerPosition(worker);
                    lastPosition[0] = 2 * position[0] - workerPosition[0];
                    lastPosition[1] = 2 * position[1] - workerPosition[1];
                    modelGame.setWorkerPosition(modelGame.getWorkerList().get(modelGame.getWorkerListPosition().indexOf(position)), lastPosition);
                }
            }
            modelGame.getCurrentState().executeState(modelGame, worker, position);
            setNextCurrentState(modelGame);
            setValidCells(modelGame, worker);
        }
    }




}
