package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.Worker;

public interface MoveEffect {
    void move(Worker worker, int[] position);
}
