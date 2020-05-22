package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.state.BuildState;
import it.polimi.ingsw.model.state.MovementState;

import java.util.ArrayList;
import java.util.List;


/**Prometheus Power Class
 * @author Piersilvio Mancuso
 */
public class PrometheusPower extends Power{

    // -------------- CONSTRUCTOR -------------

    public PrometheusPower(){
        super();
        this.textEffect = "If your Worker does not move up, it may build both\n" +
                "\t\t\t\tbefore and after moving.\n";
    }

    // ------------------ SETTER ---------------

    /**Set the turn state of the player */
    @Override
    public void setStateList(){
        super.setStateList();
        if (isActiveEffect()) stateList.add(0, new BuildState());
    }


    /**Set the valid Cells where a player can take the current State action
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player for its turn
     */
    @Override
    public void setValidCells(ModelGame modelGame, Worker worker){

        super.setValidCells(modelGame, worker);

        if (isActiveEffect()){
            int workerHeight = worker.getPosition().getHeight();
            ArrayList validPositions = (ArrayList) ((ArrayList) modelGame.getValidCells()).clone();
            if (modelGame.getCurrentState() instanceof MovementState){

                //On Movement State worker cannot move up
                for (Cell position: modelGame.getValidCells()){

                    int positionHeight = position.getHeight();

                    if (modelGame.getCurrentState() instanceof MovementState){
                        if (positionHeight > workerHeight) validPositions.remove(position);
                    }

                }
            }
            modelGame.setValidCells(validPositions);
        }



    }

}
