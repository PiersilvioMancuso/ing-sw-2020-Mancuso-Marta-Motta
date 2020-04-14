package it.polimi.ingsw.model.state;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.model.Worker;

public class EndState extends State {
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
