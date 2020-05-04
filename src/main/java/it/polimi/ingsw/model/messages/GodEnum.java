package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.god.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    /**God Enum Constructor
     * @param god is the God belong
     */
    GodEnum(God god) {
        this.god = god;
    }


    /**God Getter
     * @return the god
     */
    public God getGod() {
        return god;
    }

    /**Get all Gods that are in GodEnum into a List
     * @return a List containing all Gods in GodEnum
     */
    public static List<God> godValues(){
        List<GodEnum> godEnumList = new ArrayList<>(Arrays.asList(GodEnum.values()));
        List<God> godList = new ArrayList<>();
        for (int i = 0; i < godEnumList.size(); i++) {
            godList.add(godEnumList.get(i).getGod());
        }
        return godList;
    }

}
