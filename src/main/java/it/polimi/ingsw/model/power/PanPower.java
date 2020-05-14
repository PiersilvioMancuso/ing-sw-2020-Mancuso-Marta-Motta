package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

/**Pan Power Class
 * @author Piersilvio Mancuso
 */
public class PanPower extends Power{

    public PanPower(){
        super();
        this.textEffect = "\tYou also win if your Worker moves down two or\n" +
                "\t\t\t\tmore levels.\n";
    }

    /**Set true the power activation
     * @param activeEffect is always true
     */
    @Override
    public void setActiveEffect(boolean activeEffect) {
        this.activeEffect = true;
    }


    // ------------ Action -------------------

    /**Check if the worker used by the player let him win the game
     * @param modelGame is the game
     * @param worker is the worker used by the player
     * @param position is the position from where the worker moved up
     * @return true if the player win, otherwise false
     */
    @Override
    public boolean isWinner(ModelGame modelGame, Worker worker, Cell position){
        int workerHeight = modelGame.getWorkerPosition(worker).getHeight();
        return (super.isWinner(modelGame, worker, position) || position.getHeight() - workerHeight >= 2);
    }



}
