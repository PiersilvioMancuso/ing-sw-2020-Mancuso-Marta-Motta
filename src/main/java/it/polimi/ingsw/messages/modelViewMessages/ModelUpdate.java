package it.polimi.ingsw.messages.modelViewMessages;

import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.view.View;

/**ModelUpdate Class
 * @author Piersilvio Mancuso
 */
public class ModelUpdate extends Update{

    private ModelGame modelGame;


    // ----------- CONSTRUCTOR ----------

    /**ModelUpdate Constructor
     * @param modelGame is the modelGame that will update View
     */
    public ModelUpdate(ModelGame modelGame) {
        super();
        this.modelGame = new ModelGame(modelGame) ;
        this.className = "ModelUpdate";
    }


    // ----------- GETTER ------------

    /**ModelGame Getter
     * @return the updated ModelGame
     */
    public ModelGame getModelGame() {
        return modelGame;
    }


    // ------------- SETTER -------------

    /**ModelGame Setter
     * @param modelGame is the ModelGame updated and ready to be sent
     */
    public void setModelGame(ModelGame modelGame) {
        this.modelGame = modelGame;
    }

    @Override
    public void setChanges(View view) {
        view.setModelGame(modelGame);
        view.setAvailableCell(modelGame.getValidCells());
    }
}
