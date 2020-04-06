package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.*;

public class Minotaur extends God {

    /**Create the God Minotaur which use Minotaur Power */
    Minotaur(){
        this.power = new MinotaurPower();
    }

    /**Activate the power on a Worker
     *
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     */
    @Override
    public void activatePower(ModelGame modelGame, Worker worker) {
        power.setActiveEffect(true);
    }

    /**SetUp worker's turn
     *
     * @param modelGame is the model of the game
     * @param worker is the worker that will be used
     */
    @Override
    public void setUpTurn(ModelGame modelGame, Worker worker) {
        if (!power.isActiveEffect()) power = (Power) power;
        else power = new MinotaurPower();
        power.startPower(modelGame, worker);
    }


    /**Check if the player, who use the worker, loose the game
     *
     * @param modelGame is the model of the game
     * @param worker is the worker chosen by the player
     * @return true if the player loose, otherwise false
     */
    @Override
    public boolean isLoser(ModelGame modelGame, Worker worker) {
        if (power.getValidCells().size() == 0) return true;
        return false;
    }

    /**Execute the state of the game and, if the player wins, set the outcome of all players
     *
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the player will act using his worker
     */
    @Override
    public void executePower(ModelGame modelGame, Worker worker, int[] position) {
        if (!isLoser(modelGame, worker)){
            power.runPower(modelGame, worker, position);
            worker.getUser().setOutCome(OutCome.winsIfTrue(power.isWinner(modelGame, worker, position)));
            if (worker.getUser().getOutCome() == OutCome.WINNER){
                for (User user : modelGame.getUserList()){
                    user.setOutCome(OutCome.LOOSER);
                }
            }
        }
        else worker.getUser().setOutCome(OutCome.looseIfTrue(isLoser(modelGame, worker)));

    }

}
