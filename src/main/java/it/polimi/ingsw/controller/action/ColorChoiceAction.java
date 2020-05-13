package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.ModelColor;
import it.polimi.ingsw.model.User;

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
        super();
        this.className = "ColorChoiceAction";
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
     * @param user is the user to which the color will be set
     * @param colorList is the list of remaining colors
     */
    public void executeAction(User user, List<ModelColor> colorList){
        user.setColor(colorList.get(colorChosen));
        colorList.remove(colorChosen);
    }
}
