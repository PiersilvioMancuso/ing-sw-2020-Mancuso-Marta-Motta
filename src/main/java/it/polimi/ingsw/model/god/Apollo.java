package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.ApolloPower;
import it.polimi.ingsw.model.power.Power;

/**Apollo God Class
 * @author Piersilvio Mancuso
 */
public class Apollo extends God {

    /**Create the God Apollo which use Apollo Power */
    public Apollo(){
        this.power = new ApolloPower();
        power.setActiveEffect(false);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":\t" + power;
    }
}
