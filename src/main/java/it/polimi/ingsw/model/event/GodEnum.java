package it.polimi.ingsw.model.event;

import it.polimi.ingsw.model.god.*;

/**
 * @author Piersilvio Mancuso
 */
public enum GodEnum {
    APOLLO (new Apollo()),
    ARTEMIS (new Artemis()),
    ATHENA (new Athena()),
    ATLAS (new Atlas()),
    DEMETER (new Demeter()),
    HEPHAESTUS (new Hephaestus()),
    MINOTAUR (new Minotaur()),
    PAN (new Pan()),
    PROMETHEUS (new Prometheus());


    private final God god;


    GodEnum(God god) {
        this.god = god;
    }

    public God getGod() {
        return god;
    }
}
