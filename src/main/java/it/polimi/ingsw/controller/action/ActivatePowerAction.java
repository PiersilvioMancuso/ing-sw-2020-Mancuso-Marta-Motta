package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.controllerState.ActivatePowerControllerState;
import it.polimi.ingsw.controller.controllerState.ExecutionControllerState;
import it.polimi.ingsw.controller.controllerState.RegisterControllerState;
import it.polimi.ingsw.controller.utility.Converter;
import it.polimi.ingsw.messages.modelViewMessages.ModelUpdate;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.*;
import it.polimi.ingsw.messages.controllersMessages.Ack;
import it.polimi.ingsw.messages.controllersMessages.Nack;
import it.polimi.ingsw.model.state.BuildState;
import it.polimi.ingsw.model.state.EndState;
import it.polimi.ingsw.model.state.MovementState;
import it.polimi.ingsw.view.Command;

import java.util.ArrayList;
import java.util.List;

/**ActivatePowerAction Class
 * @author Piersilvio Mancuso
 */
public class ActivatePowerAction extends Action{

    private final Cell cell;
    private final boolean powerUp;

    // -------------- CONSTRUCTOR -------------------

    /**Activate Power Action
     * @param message is the message that will be used for the Action
     */
    public ActivatePowerAction(String message){
        super();
        this.className = "ActivatePowerAction";
        String[] messageComponent = message.split(";");
        this.username = messageComponent[0].split("=")[1];

        this.cell = Converter.convertCell(messageComponent[1]);
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

    /**Setup the player's round
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


    // ----------------- CONTROLLER ACTION -----------------

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
            modelGame = remoteController.getModelGame();

            user.getGod().looseEffect(modelGame, worker);

            modelGame = remoteController.getModelGame();

            if (modelGame.getUserList().size() == 1){
                remoteController.setResponse(new Ack(modelGame.getUserList().get(0).getUsername(),Command.WIN, new RegisterControllerState()));
            }
            else if (!modelGame.getUserList().contains(user)) {
                modelGame.nextUser();
                modelGame.nextUser();

                List<Cell> validCells = new ArrayList<>();
                for (Worker worker1 : modelGame.getWorkerList()){
                    if (worker1.getUser().equals(modelGame.getCurrentUser())) validCells.add(worker1.getPosition());
                }
                modelGame.setValidCells(validCells);

                modelGame.addUpdate(new ModelUpdate(modelGame));

                String message = "You lose the game";
                remoteController.setResponse(new Nack(message, user.getUsername(), Command.LOOSE));
                remoteController.sendResponse();



                user = modelGame.getCurrentUser();
                remoteController.saveData();
                remoteController.setResponse(new Ack(user.getUsername(), Command.USE_GOD_POWER, new ActivatePowerControllerState()));

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