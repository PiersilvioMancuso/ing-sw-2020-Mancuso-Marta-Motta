package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.event.response.*;



/**Worker Setup Action
 * @author Piersilvio Mancuso
 */
public class WorkerSetupAction extends Action{
    private Cell cell;



    //-------------- CONSTRUCTOR ---------------------

    /**Set the Cell of the Worker
     * @param string is the String which contains worker's destination cell
     */
    public WorkerSetupAction(String string){
        String [] message = string.split(";");
        username = message[0].split("=")[1];
        this.cell = new Cell(message[1].charAt(5) - '0', message[1].charAt(7) - '0');
    }



    //---------------- GETTER ------------------

    /**Getter Cell
     * @return the Cell
     */
    public Cell getCell() {
        return cell;
    }

    /**Get the object instance
     * @return the instance of the class
     */
    public Class getInstance(){
        return WorkerSetupAction.class;
    }


    // ---------------- ACTION ---------------

    /**Execute the Worker Setup Action
     * @param modelGame is the model of the game
     */
    public void executeAction(ModelGame modelGame, User user){

        Worker worker = new Worker(user);
        modelGame.addWorker(worker);

        cell = modelGame.getBoard().getCell(cell);
        modelGame.getCurrentState().executeState(modelGame,worker, cell);


    }


}
