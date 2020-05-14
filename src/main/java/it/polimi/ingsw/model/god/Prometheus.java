package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.*;


/**Prometheus God Class
 * @author Piersilvio Mancuso
 */
public class Prometheus extends God {

    /**Create the God Prometheus which use Prometheus Power */
    public Prometheus(){
        this.power = new PrometheusPower();
        power.setActiveEffect(false);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":\t" + power;
    }
}
