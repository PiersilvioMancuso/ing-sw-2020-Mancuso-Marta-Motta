package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.controllerState.ExecutionControllerState;
import it.polimi.ingsw.controller.controllerState.RegisterControllerState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.*;
import it.polimi.ingsw.model.messages.controllersMessages.Ack;
import it.polimi.ingsw.model.messages.controllersMessages.Nack;
import it.polimi.ingsw.model.state.BuildState;
import it.polimi.ingsw.model.state.MovementState;
import it.polimi.ingsw.view.Command;

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
        super();
        this.className = "ActivatePowerAction";
        String[] messageComponent = message.split(";");
        this.username = messageComponent[0].split("=")[1];
        String cellString = messageComponent[1].split("=")[1];
        String regex = "[+-,;'*.^-]*";
        cellString = cellString.replaceAll(regex, "");

        this.cell = new Cell(Integer.parseInt(cellString.substring(1))%10, cellString.charAt(0) - 'A' );
        this.powerUp = messageComponent[2].toLowerCase().split("=")[1].replaceAll(" ", "").charAt(0) == 'y';

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



    // --------------- ACTION -------------------

    /**Execute The Activate Power Effect
     * @param modelGame is the model of the game
     * @param worker is the worker that will be used
     */
    public void executeAction(ModelGame modelGame, Worker worker){
        God godInGame = worker.getUser().getGod();

        if (powerUp) {
            godInGame.activatePower(modelGame, worker);
        }
        else{
            godInGame.getPower().setActiveEffect(false);
        }
        godInGame.setUpTurn(modelGame,worker);

    }


    /**Setup user's turn and, if powerUp == true, activate his power
     * @param remoteController is the remoteController that will execute the action
     */
    @Override
    public void controlAction(RemoteController remoteController) {
        User user = remoteController.getUserFromUsername(username);
        ModelGame modelGame = remoteController.getModelGame();

        if (!modelGame.getValidCells().contains(cell)){
            String message = "Invalid Cell selected";
            remoteController.setResponse(new Nack(message, user.getUsername(), Command.USE_GOD_POWER));
        }
        else {
            Worker worker = remoteController.getModelGame().getWorkerFromPosition(cell);
            remoteController.setCurrentWorker( worker);
            executeAction(modelGame, worker);

            user.getGod().looseEffect(modelGame, worker);

            if (modelGame.getUserList().size() == 1){
                remoteController.setResponse(new Ack(user.getUsername(),Command.WIN, new RegisterControllerState()));
            }

            else {
                if (modelGame.getCurrentState() instanceof MovementState){
                    remoteController.setResponse(new Ack(user.getUsername(), Command.MOVE, new ExecutionControllerState()));
                }
                else if (modelGame.getCurrentState() instanceof BuildState){
                    remoteController.setResponse(new Ack(user.getUsername(), Command.BUILD, new ExecutionControllerState()));
                }
            }

        }
    }
}