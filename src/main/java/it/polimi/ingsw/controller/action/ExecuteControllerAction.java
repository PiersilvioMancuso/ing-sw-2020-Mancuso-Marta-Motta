package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.controller.controllerState.ActivatePowerControllerState;
import it.polimi.ingsw.controller.controllerState.ExecutionControllerState;
import it.polimi.ingsw.controller.controllerState.RegisterControllerState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.*;
import it.polimi.ingsw.messages.controllersMessages.Ack;
import it.polimi.ingsw.messages.controllersMessages.Nack;
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



    // ----------------- CONTROLLER ACTION -----------------

    /**Execute the Action
     * @param remoteController is the remoteController that will execute the action
     */
    @Override
    public void controlAction(RemoteController remoteController) {

        //Setup the objects needed
        ModelGame modelGame = remoteController.getModelGame();
        Worker currentWorker = remoteController.getCurrentWorker();
        Cell lastWorkerPosition = modelGame.getWorkerPosition(currentWorker);
        User user = remoteController.getUserFromUsername(username);
        cell = modelGame.getBoard().getCell(cell);


        //If the cell selected is not a valid Cell, it will send a Nack to the Client
        if (!modelGame.getValidCells().contains(cell)){
            String message = "Invalid Cell selected";

            if (modelGame.getCurrentState() instanceof BuildState){
                remoteController.setResponse( new Nack(message, username, Command.BUILD));
            }
            else if (modelGame.getCurrentState() instanceof MovementState){
                remoteController.setResponse(new Nack(message, username, Command.MOVE));
            }

        }

        //If the cell is a valid Cell the action will be executed
        else {
            executeAction(modelGame, currentWorker);
            user.getGod().winEffect(modelGame, currentWorker, lastWorkerPosition);
            List<User> playerList = remoteController.getPlayerList();

            //If after the execution, the player is winner, all the players will receive an Ack telling them what's their Outcome and another game will be created
            if (user.getOutCome().equals(OutCome.WINNER)) {
                for (User user1 : playerList) {
                    if (!user1.equals(user)) {
                        remoteController.setResponse(new Ack(user1.getUsername(), Command.LOOSE, new RegisterControllerState()));
                        remoteController.sendResponse();
                    }
                }
                remoteController.setResponse(new Ack(user.getUsername(), Command.WIN, new RegisterControllerState()));
                remoteController.setGameEnded(true);
            }

            //If after the execution the game is not ended
            else {
                user.getGod().looseEffect(modelGame, currentWorker);

                //If the current user lose the game, and there is just one player in the game, the other player win,
                //so each player will receive his Outcome and a new game will be created
                if (modelGame.getUserList().size() == 1) {
                    remoteController.setResponse(new Ack(username, Command.LOOSE, new RegisterControllerState()));
                    remoteController.sendResponse();

                    user = modelGame.getUserList().get(0);


                    remoteController.setResponse(new Ack(user.getUsername(), Command.WIN, new RegisterControllerState()));
                    remoteController.setGameEnded(true);
                }

                //Else if the user ended his turn, the game will be saved into the file "model.xml" and the next user will be notice that he
                //has to select his worker and if he wants to activate his God's Power
                else if (modelGame.getCurrentState() instanceof EndState) {
                    remoteController.setModelCopy(remoteController.getModelGame());
                    remoteController.saveData();
                    remoteController.setResponse(new Ack(modelGame.getCurrentUser().getUsername(), Command.USE_GOD_POWER, new ActivatePowerControllerState()));
                }

                //If the user lose the game he will receive a message telling him
                else if (!modelGame.getUserList().contains(user)) {
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
