package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.state.BuildState;
import it.polimi.ingsw.model.state.MovementState;

import java.util.ArrayList;
import java.util.List;

/**Minotaur Power Class
 * @author Piersilvio Mancuso
 */
public class MinotaurPower extends Power{

    public MinotaurPower(){
        super();
        this.textEffect = "Your Worker may move into an opponent Worker’s space, if \n" +
                "\t\t\ttheir Worker can be forced one space straight backwards \n" +
                "\t\t\tto an unoccupied space at any level.";
    }

    /**Calculate the destination Cell of Minotaur Push Movement
     * @param firstCell is the Cell from where Minotaur's Worker Move
     * @param secondCell is the Cell from where the other user's worker will be pushed
     * @return the position of the other user's worker
     */
    public Cell minotaurCalculator(Cell firstCell, Cell secondCell){
        int cellX = 2*secondCell.getX() - firstCell.getX();
        int cellY = 2*secondCell.getY() - firstCell.getY();
        return new Cell(cellX,cellY);
    }

    // -------------- Setter -------------------

    /**Set the valid Cells where a player can take the current State action
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     */
    @Override
    public void setValidCells(ModelGame modelGame, Worker worker){

        super.setValidCells(modelGame, worker);

        if (isActiveEffect()){
            Cell workerPosition = worker.getPosition();

            // During Movement State insert into validCells other workers position if these workers :
            if (modelGame.getCurrentState() instanceof MovementState){

                //1)are in a NeighbourCell;
                for (Cell position: modelGame.getBoard().getNeighbourCell(workerPosition)){

                    if (modelGame.getWorkerListPosition().contains(position)){
                        User user = modelGame.getWorkerFromPosition(position).getUser();

                        //2)are controlled by other users
                        if (!worker.getUser().equals(user)){

                            //calculate the destination Cell
                            Cell lastPosition = minotaurCalculator(workerPosition, position);
                            lastPosition = modelGame.getBoard().getCell(lastPosition);

                            //3)have a free Cell in the same direction
                            if (modelGame.getBoard().getNeighbourCell(position).contains(lastPosition) && lastPosition.getHeight() < 4 && !modelGame.getWorkerListPosition().contains(lastPosition)){
                                if (!modelGame.getValidCells().contains(position)) modelGame.getValidCells().add(position);
                            }
                        }

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
    @Override
    public void runPower(ModelGame modelGame, Worker worker, Cell position) throws IllegalArgumentException{
        if (!(modelGame.getCurrentState() instanceof MovementState) || !isActiveEffect()) super.runPower(modelGame, worker, position);

        else {
            if (!modelGame.getValidCells().contains(position)) throw new IllegalArgumentException("Position is not Valid");

            //If position is occupied by another worker, it will be pushed in the next Cell on the same direction
            if (modelGame.getWorkerListPosition().contains(position)){
                Cell workerPosition = modelGame.getWorkerPosition(worker);

                //1)calculate the destination Cell
                Cell lastPosition = minotaurCalculator(workerPosition,position);
                lastPosition = modelGame.getBoard().getCell(lastPosition);

                //2)push other user's worker to lastPosition
                modelGame.setWorkerPosition(modelGame.getWorkerFromPosition(position), lastPosition);
            }

            //Run the State Action
            modelGame.getCurrentState().executeState(modelGame, worker, position);
            setNextCurrentState(modelGame);
            setValidCells(modelGame, worker);


        }
    }




}
