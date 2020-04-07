package it.polimi.ingsw.model;

public class EndState extends State {
    @Override
    public void executeState(ModelGame modelGame, Worker worker, Cell position) {
        if (modelGame == null || worker == null) throw new NullPointerException("Parameters modelGame and worker cannot be null");

        else {
            User user = worker.getUser();
            user.getGod().getPower().setActiveEffect(false);
            modelGame.nextUser();
        }

    }
}
