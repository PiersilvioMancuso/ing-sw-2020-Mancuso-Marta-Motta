package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.power.BuildEffect;
import it.polimi.ingsw.model.power.MoveEffect;
import it.polimi.ingsw.model.power.WinEffect;

abstract public class God implements WinEffect, BuildEffect, MoveEffect {
    private static boolean athenaEffect = false;

    @Override
    public void build(int[] position) {

    }

    @Override
    public void move(Worker worker, int[] position) {

    }

    @Override
    public boolean checkWin() {
        return false;
    }

    public boolean getAthenaEffect() {
        return false;
    }

    public static void setAthenaEffect(boolean athenaEffect) {
    }

    abstract public void effect(Worker worker, int[] position);
}
