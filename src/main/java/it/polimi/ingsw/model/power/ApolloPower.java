package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.state.BuildState;
import it.polimi.ingsw.model.state.MovementState;

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


        super.setValidCells(modelGame, worker);

        if (isActiveEffect()){
            /* During Movement State insert into validCells other workers position if these workers :
                1)are in a NeighbourCell;
                2)are controlled by other users
        * */
            Cell workerPosition = worker.getPosition();

            if (modelGame.getCurrentState() instanceof MovementState){
                User userWorker = worker.getUser();
                for (Worker worker1 : modelGame.getWorkerList()){
                    if (!worker1.getUser().equals(userWorker) && modelGame.getBoard().getNeighbourCell(workerPosition).contains(worker1.getPosition())){
                        modelGame.getValidCells().add(worker1.getPosition());
                    }
                }
            }
            athenaEffectModification(modelGame, worker);
        }

    }


    // ------------ Action -------------------

    /**Execute the state action
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the action will take place
     * @exception IllegalArgumentException if position is not a valid cell
     */
    public void runPower(ModelGame modelGame, Worker worker, Cell position){
        if (!isActiveEffect()) super.runPower(modelGame, worker, position);

        else {
            //Check if Position is a Valid Cell
            if (modelGame.getCurrentState() instanceof MovementState || modelGame.getCurrentState() instanceof BuildState) {
                if (!modelGame.getValidCells().contains(position)) throw new IllegalArgumentException("Position is Invalid");
            }

            //Check if in position there is a worker controlled by another User during MovementState
            if (modelGame.getCurrentState() instanceof MovementState){

                //If position is occupied by another worker controlled by another user, his position will be switched with worker's position
                if (modelGame.getWorkerListPosition().contains(position)){
                    Cell workerPosition = worker.getPosition();
                    Worker otherWorker = modelGame.getWorkerFromPosition(position);
                    modelGame.setWorkerPosition(otherWorker, workerPosition);
                }

            }

            //Execute the State Action
            modelGame.getCurrentState().executeState(modelGame, worker, position);

            //Set the next state of the turn
            setNextCurrentState(modelGame);

            //Set the valid Cells
            setValidCells(modelGame, worker);

        }
    }




}
