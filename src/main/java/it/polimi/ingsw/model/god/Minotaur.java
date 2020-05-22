package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.*;

/**Minotaur God Class
 * @author Piersilvio Mancuso
 */
public class Minotaur extends God {

    // ----------------- CONSTRUCTOR --------------

    /**Create the God Minotaur which use Minotaur Power */
    public Minotaur(){
        this.power = new MinotaurPower();
        power.setActiveEffect(false);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":\t" + power;
    }

}
