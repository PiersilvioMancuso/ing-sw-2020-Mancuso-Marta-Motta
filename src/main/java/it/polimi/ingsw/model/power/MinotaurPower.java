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

    // -------------- Setter -------------------

    /**Set the valid Cells where a player can take the current State action
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     */
    @Override
    public void setValidCells(ModelGame modelGame, Worker worker){
        List<Cell> validPositions = new ArrayList<>();
        Cell workerPosition = modelGame.getWorkerPosition(worker);
        int workerHeight = workerPosition.getHeight();

        // Insert into validCells all the Neighbours Cell of Worker Position, except Dome and Occupied Cells
        for (Cell position: modelGame.getBoard().getNeighbourCell(workerPosition)){
            int positionHeight = position.getHeight();

            if (!(positionHeight > workerHeight + 1 || positionHeight == 4 || modelGame.getWorkerListPosition().contains(position))) {
                validPositions.add(position);
            }
        }


        if (modelGame.getCurrentState() instanceof MovementState){
            for (int i = 0; i < modelGame.getBoard().getNeighbourCell(workerPosition).size(); i++){
                Cell position = modelGame.getWorkerListPosition().get(i);


                /* During Movement State insert into validCells other workers position if these workers :
                 1)are in a NeighbourCell;
                 2)are controlled by other users
                 3)have a free Cell in the same direction
                * */
                if (modelGame.getWorkerListPosition().contains(position)){
                    User user = modelGame.getWorkerList().get(modelGame.getWorkerListPosition().indexOf(position)).getUser();

                    if (!worker.getUser().equals(user)){
                        Cell lastPosition = new Cell(2 * position.getX() - workerPosition.getX(), 2 * position.getY() - workerPosition.getY());
                        int index = modelGame.getBoard().getBuildMap().indexOf(lastPosition);
                        lastPosition = modelGame.getBoard().getBuildMap().get(index);


                        if (modelGame.getBoard().getNeighbourCell(position).contains(lastPosition) && lastPosition.getHeight() < 4 && !modelGame.getWorkerListPosition().contains(lastPosition)){
                            if (!validPositions.contains(position)) validPositions.add(position);
                        }
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
    public void runPower(ModelGame modelGame, Worker worker, Cell position){
        if (modelGame.getCurrentState() instanceof MovementState || modelGame.getCurrentState() instanceof BuildState){
            if (modelGame.getCurrentState() instanceof MovementState){

                //If position is occupied by another worker, it will be pushed in the next Cell on the same direction
                if (modelGame.getWorkerListPosition().contains(position)){
                    Cell workerPosition = modelGame.getWorkerPosition(worker);

                    Cell lastPosition = new Cell(2 * position.getX() - workerPosition.getX(), 2 * position.getY() - workerPosition.getY());
                    int index = modelGame.getBoard().getBuildMap().indexOf(lastPosition);
                    lastPosition = modelGame.getBoard().getBuildMap().get(index);


                    modelGame.setWorkerPosition(modelGame.getWorkerList().get(modelGame.getWorkerListPosition().indexOf(position)), lastPosition);
                }
            }

            //Run the State Action
            modelGame.getCurrentState().executeState(modelGame, worker, position);
            setNextCurrentState(modelGame);
            setValidCells(modelGame, worker);
        }
    }




}
