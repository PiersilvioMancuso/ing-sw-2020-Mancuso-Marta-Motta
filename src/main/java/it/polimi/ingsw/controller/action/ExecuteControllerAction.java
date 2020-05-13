package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.*;

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
        String[] cellCord = cellString.split("[, ?.@]+");
        this.cell = new Cell(Integer.parseInt(cellCord[0]), Integer.parseInt(cellCord[1]));

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
}
