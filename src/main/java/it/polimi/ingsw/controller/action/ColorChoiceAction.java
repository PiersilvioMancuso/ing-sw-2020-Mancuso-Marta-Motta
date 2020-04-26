package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.User;
import java.awt.*;

import java.util.List;

/**Color Choice Action Class
 * @author Piersilvio Mancuso
 */
public class ColorChoiceAction extends Action{

    private int colorChosen;


    /**Color Choice Action Constructor
     * @param data is a String with the following pattern: 'username=%userName,color=%color'
     */
    public ColorChoiceAction(String data){
        String[] message = data.split(";");
        this.username = message[0].split("=")[1];
        this.colorChosen = Integer.parseInt(message[1].split("=")[1]);
    }

    /**Color Choice Getter
     * @return an Integer who represents the color Chosen
     */
    public int getColorChosen() {
        return colorChosen;
    }


    /**Set User's color
     * @param modelGame is the model of the game
     * @param colorList is the list of remaining colors
     */
    public void executeAction(ModelGame modelGame, List<Color> colorList){
        User user = modelGame.getUserFromUsername(username);
        user.setColor(colorList.get(colorChosen));
    }
}
