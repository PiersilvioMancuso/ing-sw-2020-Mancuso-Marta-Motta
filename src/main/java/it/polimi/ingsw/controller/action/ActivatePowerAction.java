package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.*;

/**Activate Power Action
 * @author Piersilvio Mancuso
 */
public class ActivatePowerAction extends Action{

    private Cell cell;
    private boolean powerUp;

    // -------------- CONSTRUCTOR -------------------

    /**String string = "cell=";
     * @param message is a String with the following pattern: 'username=%userName,cell=2-4,powerUp='
     */
    public ActivatePowerAction(String message){
        String[] messageComponent = message.split(";");
        this.username = messageComponent[0];
        this.cell = new Cell(messageComponent[1].charAt(5) - '0', messageComponent[0].charAt(7) - '0');
        this.powerUp = messageComponent[2].toLowerCase().charAt(7) == 'y';
    }



    // ------------- GETTER ------------------

    /**Get the Cell of the Worker
     * @return the Cell of the worker
     */
    public Cell getCell() {
        return cell;
    }


    /**Get Power Up
     * @return the boolean value of the activation Power
     */
    public boolean isPowerUp() {
        return powerUp;
    }


    /**Get the object instance
     * @return the instance of the class
     */
    public Class getInstance(){
        return ActivatePowerAction.class;
    }


    // --------------- ACTION -------------------

    /**Execute The Activate Power Effect
     * @param modelGame is the model of the game
     * @param worker is the worker that will be used
     */
    public void executeAction(ModelGame modelGame, Worker worker){
        God godInGame = worker.getUser().getGod();

        if (powerUp) godInGame.activatePower(modelGame, worker);
        godInGame.setUpTurn(modelGame,worker);

    }


}