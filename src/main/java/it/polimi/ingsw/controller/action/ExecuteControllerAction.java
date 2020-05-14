package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.controllerState.ActivatePowerControllerState;
import it.polimi.ingsw.controller.controllerState.ExecutionControllerState;
import it.polimi.ingsw.controller.controllerState.RegisterControllerState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.*;
import it.polimi.ingsw.model.messages.controllersMessages.Ack;
import it.polimi.ingsw.model.messages.controllersMessages.Nack;
import it.polimi.ingsw.model.state.BuildState;
import it.polimi.ingsw.model.state.EndState;
import it.polimi.ingsw.model.state.MovementState;
import it.polimi.ingsw.view.Command;

import java.util.List;

/**Execute State Action
 * @author Piersilvio Mancuso
 */
public class ExecuteControllerAction extends Action{
    private Cell cell;

    // --------------- CONSTRUCTOR ----------------

    /**Create the Execute Controller Action Object
     * @param string is the string containing the position where the action will be executed
     */
    public ExecuteControllerAction(String string){
        super();
        this.className = "ExecuteControllerAction";
        String[] message = string.split(";");
        this.username = message[0].split("=")[1];
        String cellString = message[1].split("=")[1];
        String regex = "[+-,;'*.^-]*";
        cellString = cellString.replaceAll(regex, "");
        this.cell = new Cell(Integer.parseInt(cellString.substring(1))%10, cellString.charAt(0) - 'A' );
    }


    //----------- GETTER ------------------

    /**Getter Cell
     * @return the Cell where to execute the Action
     */
    public Cell getCell() {
        return cell;
    }



    // ------------- ACTION -------------------

    /**Execute the current Controller Action
     * @param modelGame is the model of the game
     * @param worker is the worker that will do the Action
     */
    public void executeAction(ModelGame modelGame, Worker worker){
        God god = worker.getUser().getGod();
        god.executePower(modelGame, worker, cell);
    }


    @Override
    public void controlAction(RemoteController remoteController) {

        ModelGame modelGame = remoteController.getModelGame();
        Worker currentWorker = remoteController.getCurrentWorker();
        Cell lastWorkerPosition = modelGame.getWorkerPosition(currentWorker);
        User user = remoteController.getUserFromUsername(username);
        cell = modelGame.getBoard().getCell(cell);


        //Invalid Cell
        if (!modelGame.getValidCells().contains(cell)){
            String message = "Invalid Cell selected";

            if (modelGame.getCurrentState() instanceof BuildState){
                remoteController.setResponse( new Nack(message, username, Command.BUILD));
            }
            else if (modelGame.getCurrentState() instanceof MovementState){
                remoteController.setResponse(new Nack(message, username, Command.MOVE));
            }

        }

        //ValidCell

        else {
            executeAction(modelGame, currentWorker);
            user.getGod().winEffect(modelGame, currentWorker, lastWorkerPosition);
            List<User> playerList = remoteController.getPlayerList();

            //If winner
            if (user.getOutCome().equals(OutCome.WINNER)) {
                for (User user1 : playerList) {
                    if (!user1.equals(user)) {
                        remoteController.setResponse(new Ack(user1.getUsername(), Command.LOOSE, new RegisterControllerState()));
                        remoteController.sendResponse();
                    }
                }
                remoteController.setResponse(new Ack(user.getUsername(), Command.WIN, new RegisterControllerState()));
                remoteController.setGameEnded(true);
            } else {
                user.getGod().looseEffect(modelGame, currentWorker);

                if (modelGame.getUserList().size() == 1) {
                    user = modelGame.getUserList().get(0);
                    for (User user1 : playerList) {
                        if (!user1.equals(user)) {
                            remoteController.setResponse(new Ack(user1.getUsername(), Command.LOOSE, new RegisterControllerState()));
                            remoteController.sendResponse();
                        }
                    }

                    remoteController.setResponse(new Ack(user.getUsername(), Command.WIN, new RegisterControllerState()));
                    remoteController.setGameEnded(true);
                }

                else if (modelGame.getCurrentState() instanceof EndState) {
                    remoteController.setResponse(new Ack(modelGame.getCurrentUser().getUsername(), Command.USE_GOD_POWER, new ActivatePowerControllerState()));
                } else if (!modelGame.getUserList().contains(user)) {
                    String message = "You already lose the game";
                    remoteController.setResponse(new Nack(message, user.getUsername(), Command.QUIT));
                } else {
                    if (modelGame.getCurrentState() instanceof BuildState) {
                        remoteController.setResponse(new Ack(user.getUsername(), Command.BUILD, new ExecutionControllerState()));
                    } else remoteController.setResponse(new Ack(user.getUsername(), Command.MOVE, new ExecutionControllerState()));
                }

            }
        }

    }

}
