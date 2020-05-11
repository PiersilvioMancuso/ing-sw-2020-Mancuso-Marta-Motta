package it.polimi.ingsw.model.messages.modelViewMessages;

import it.polimi.ingsw.model.ModelColor;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.List;

/**ModelColorListUpdate Class
 * @author Piersilvio Mancuso
 */
public class ModelColorListUpdate extends Update{

    private ArrayList<ModelColor> modelColorList;

    // --------------- CONSTRUCTOR -------------

    /**ModelColorListUpdate Constructor
     * @param modelColorList is a list of modelColor
     */
    public ModelColorListUpdate(ArrayList<ModelColor> modelColorList) {
        this.modelColorList = (ArrayList)modelColorList.clone();
    }


    // ------------ GETTER ------------

    /**ModelColorList Getter
     * @return a list of all ModelColors that can be set to players
     */
    public List<ModelColor> getModelColorList() {
        return modelColorList;
    }


    // ------------ SETTER ------------

    /**ModelColorList Setter
     * @param modelColorList is a list of all ModelColors that can be set to players
     */
    public void setModelColorList(ArrayList<ModelColor> modelColorList) {
        this.modelColorList = (ArrayList)modelColorList.clone();
    }


    @Override
    public void setChanges(View view) {
        view.setAvailableColor(modelColorList);
    }
}
