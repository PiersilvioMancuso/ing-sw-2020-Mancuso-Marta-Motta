package it.polimi.ingsw.model;

public class EndState extends State {
    @Override
    public void executeState(ModelGame modelGame, Worker worker, int[] position) {
        User user = worker.getUser();
        user.getGod().getPower().setActiveEffect(false);
        modelGame.nextUser();
    }
}
