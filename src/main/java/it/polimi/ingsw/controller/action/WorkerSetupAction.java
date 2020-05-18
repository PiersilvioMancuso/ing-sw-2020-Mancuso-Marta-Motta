package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.controllerState.ActivatePowerControllerState;
import it.polimi.ingsw.controller.controllerState.WorkerSetupControllerState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.messages.controllersMessages.Ack;
import it.polimi.ingsw.messages.controllersMessages.Nack;
import it.polimi.ingsw.messages.modelViewMessages.ModelUpdate;
import it.polimi.ingsw.view.Command;

import java.util.ArrayList;
import java.util.List;


/**Worker Setup Action
 * @author Piersilvio Mancuso
 */
public class WorkerSetupAction extends Action{
    private Cell cell;



    //-------------- CONSTRUCTOR ---------------------

    /**Set the Cell of the Worker
     * @param string is the String which contains worker's destination cell
     */
    public WorkerSetupAction(String string) {
        super();
        this.className = "WorkerSetupAction";
        String[] message = string.split(";");
        username = message[0].split("=")[1];
        String cellString = message[1].split("=")[1];
        String regex = "[+-,;'*.^-]*";
        cellString = cellString.replaceAll(regex, "");

        this.cell = new Cell(Integer.parseInt(cellString.substring(1)) % 10, cellString.charAt(0) - 'A' );
    }


    //---------------- GETTER ------------------

    /**Getter Cell
     * @return the Cell where to put the worker
     */
    public Cell getCell() {
        return cell;
    }



    // ---------------- ACTION ---------------

    /**Execute the Worker Setup Action
     * @param modelGame is the model of the game
     */
    public void executeAction(ModelGame modelGame, User user){

        Worker worker = new Worker(user);
        modelGame.addWorker(worker);


        cell = modelGame.getBoard().getCell(cell);
        modelGame.getCurrentState().executeState(modelGame, worker, cell);
        user.getGod().getPower().setValidCells(modelGame, worker);

    }

    /**Set worker's start position
     * @param remoteController is the Remote Controller that will execute the action
     */
    public void controlAction(RemoteController remoteController){
        ModelGame modelGame = remoteController.getModelGame();
        User user =  modelGame.getUserFromUsername(username);

        //If cell is not a free cell, or it is not in the Board, remoteController will send a Nack saying that an Invalid Cell was selected
        if (modelGame.getWorkerListPosition().contains(cell) || !modelGame.getBoard().getBuildMap().contains(cell)){
            String errorMessage = "Invalid Cell selected";
            remoteController.setResponse( new Nack(errorMessage, username, Command.SET_WORKER_POSITION));
        }

        else {

            //Set worker's position
            executeAction(modelGame, user);

            //If user has already set all of his workers
            if (remoteController.userCompleteSetup(user)) {

                //Set the next user
                modelGame.nextUser();
                user = modelGame.getCurrentUser();

                //If the next user has already set all of his workers too
                if (remoteController.userCompleteSetup(user)) {

                    //Set the valid cells for the Activation Power
                    List<Cell> validCells = new ArrayList<>();
                    for (Worker worker : modelGame.getWorkerList()){
                        if (worker.getUser().equals(user)) validCells.add(worker.getPosition());
                    }

                    modelGame.setValidCells(validCells);
                    modelGame.addUpdate(new ModelUpdate(modelGame));

                    remoteController.setResponse( new Ack(user.getUsername(), Command.USE_GOD_POWER, new ActivatePowerControllerState()));
                }

                else {
                    List<Cell> cells = new ArrayList<>(modelGame.getBoard().getBuildMap());
                    for (Worker worker: modelGame.getWorkerList()){
                        if (cells.contains(worker.getPosition())) cells.remove(worker.getPosition());
                    }
                    modelGame.setValidCells(cells);
                    modelGame.addUpdate(new ModelUpdate(modelGame));

                    remoteController.setResponse(new Ack(user.getUsername(), Command.SET_WORKER_POSITION, new WorkerSetupControllerState()));
                }


            }

            //If user has not already set all of his workers, it will receive an WorkerSetup Ack
            else {
                List<Cell> cells = new ArrayList<>(modelGame.getBoard().getBuildMap());
                for (Worker worker: modelGame.getWorkerList()){
                    if (cells.contains(worker.getPosition())) cells.remove(worker.getPosition());
                }
                modelGame.setValidCells(cells);
                modelGame.addUpdate(new ModelUpdate(modelGame));

                remoteController.setResponse(new Ack(username, Command.SET_WORKER_POSITION, new WorkerSetupControllerState()));
            }

        }
    }


}
