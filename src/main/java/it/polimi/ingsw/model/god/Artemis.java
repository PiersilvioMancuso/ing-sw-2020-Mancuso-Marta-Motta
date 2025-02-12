package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.*;

/**Artemis God Class
 * @author Piersilvio Mancuso
 */
public class Artemis extends God {

    // ------------------ CONSTRUCTOR -----------------

    /**Create the God Artemis which use Artemis Power */
    public Artemis(){
        this.power = new ArtemisPower();
        power.setActiveEffect(false);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":\t" + power;
    }

}
