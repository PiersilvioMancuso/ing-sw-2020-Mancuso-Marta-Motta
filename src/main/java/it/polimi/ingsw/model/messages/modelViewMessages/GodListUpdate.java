package it.polimi.ingsw.model.messages.modelViewMessages;

import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.model.messages.GodEnum;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.List;

/**GodListUpdate Class
 * @author Piersilvio Mancuso
 */
public class GodListUpdate extends Update{

    private ArrayList<God> gods;

    // ---------------- CONSTRUCTOR --------------

    /**GodListUpdate Constructor
     * @param godEnumList
     */
    public GodListUpdate(List<GodEnum> godEnumList) {
        super();
        this.gods = new ArrayList<>();

        for (GodEnum godEnum : godEnumList){
            this.gods.add(godEnum.getGod());
        }
    }


    // -----------------  GETTER -----------------

    /**Gods Getter
     * @return an ArrayList containing all Gods from which first Player will choose the ones in game,
     * while the others will choose which one to use
     */
    public ArrayList<God> getGods() {
        return gods;
    }


    // ------------------ SETTER ----------------

    /**Gods Setter
     * @param godEnumList is an ArrayList containing all Gods from which first Player will choose the ones in game,
     * while the others will choose which one to use
     */
    public void setGods(List<GodEnum> godEnumList) {
        this.gods = new ArrayList<>();

        for (GodEnum godEnum : godEnumList){
            this.gods.add(godEnum.getGod());
        }
    }

    @Override
    public void setChanges(View view) {
        view.setAvailableGod(gods);
    }
}
