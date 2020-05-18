package it.polimi.ingsw.model.state;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.model.Worker;

/**EndState Class
 * @author Piersilvio Mancuso
 */
public class EndState extends State {

    /**Set the next user
     * @param modelGame is the model of the game
     * @param worker is the worker who will execute the action
     * @param position is the position where the action will be acted
     */
    @Override
    public void executeState(ModelGame modelGame, Worker worker, Cell position) {
        if (modelGame == null || worker == null) throw new NullPointerException("Parameters modelGame and worker cannot be null");
        if (!modelGame.getWorkerList().contains(worker)) throw new IllegalArgumentException("Worker is not in the Game");

        else {
            User user = worker.getUser();
            user.getGod().getPower().setActiveEffect(false);
            modelGame.nextUser();
        }

    }
}
