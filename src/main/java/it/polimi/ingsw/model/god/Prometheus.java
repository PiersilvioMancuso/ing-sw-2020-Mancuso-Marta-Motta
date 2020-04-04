package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.*;

public class Prometheus extends God {

    /**Create the God Apollo which use Apollo Power */
    Prometheus(){
        this.power = new PrometheusPower();
    }

    /**Activate the power on a Worker
     *
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     */
    @Override
    void activatePower(ModelGame modelGame, Worker worker) {
        power.setActiveEffect(true);
    }

    /**SetUp worker's turn
     *
     * @param modelGame is the model of the game
     * @param worker is the worker that will be used
     */
    @Override
    void setUpTurn(ModelGame modelGame, Worker worker) {
        if (!power.isActiveEffect()) power = (Power) power;
        power.startPower(modelGame, worker);
    }


    /**Check if the player, who use the worker, loose the game
     *
     * @param modelGame is the model of the game
     * @param worker is the worker chosen by the player
     * @return true if the player loose, otherwise false
     */
    @Override
    boolean isLoser(ModelGame modelGame, Worker worker) {
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
    void executePower(ModelGame modelGame, Worker worker, int[] position) {
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
