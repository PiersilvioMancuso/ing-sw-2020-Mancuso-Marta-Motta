package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.*;


/**Demeter God Class
 * @author Piersilvio Mancuso
 */
public class Demeter extends God {

    /**Create the God Demeter which use Demeter Power */
    public Demeter(){
        this.power = new DemeterPower();
        power.setActiveEffect(false);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":\t" + power;
    }

}
