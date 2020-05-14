package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.*;

/**Atlas God Class
 * @author Piersilvio Mancuso
 */
public class Atlas extends God {

    /**Create the God Atlas which use Atlas Power */
    public Atlas(){
        this.power = new AtlasPower();
        power.setActiveEffect(false);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":\t" + power;
    }

}
