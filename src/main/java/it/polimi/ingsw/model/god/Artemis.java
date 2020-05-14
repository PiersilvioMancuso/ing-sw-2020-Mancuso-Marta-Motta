package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.*;

/**Artemis God Class
 * @author Piersilvio Mancuso
 */
public class Artemis extends God {

    /**Create the God Artemis which use Artemis Power */
    public Artemis(){
        this.power = new ArtemisPower();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":\t" + power;
    }

}
