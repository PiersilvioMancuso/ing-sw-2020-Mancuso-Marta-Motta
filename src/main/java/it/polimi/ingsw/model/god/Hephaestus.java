package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.power.*;

/**Hephaestus God Class
 * @author Piersilvio Mancuso
 */
public class Hephaestus extends God {


    /**Create the God Hephaestus which use Hephaestus Power */
    public Hephaestus(){
        this.power = new HephaestusPower();
        power.setActiveEffect(false);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":\t" + power;
    }
}
