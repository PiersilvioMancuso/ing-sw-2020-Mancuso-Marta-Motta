package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.RemoteController;
import it.polimi.ingsw.messages.controllersMessages.Nack;
import it.polimi.ingsw.view.Command;

/**Players In Game Choice Action
 * @author Piersilvio Mancuso
 */
public class PlayersInGameChoiceAction extends Action{

    private final int numberOfPlayers;

    // ---------------- CONSTRUCTOR ---------------

    /**PlayersInGameConstructor
     * @param string is the string that will be analyzed to set the Action data
     */
    public PlayersInGameChoiceAction(String string){
        super();
        this.className = "PlayersInGameChoiceAction";

        String[] message = string.split(";");
        this.username = message[0].split("=")[1];
        this.numberOfPlayers = Integer.parseInt(message[1].split("=")[1]);

    }


    // --------------- GETTER --------------------

    /**Number of Players Getter
     * @return the number of the players that can be in the same game
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }



    // ------------- EFFECT -------------------

    /**Set the max number of players in the game
     * @param remoteController is the remote Controller to which the max number of players will be set
     */
    public void executeAction(RemoteController remoteController){
        remoteController.setMaxPlayers(numberOfPlayers);
    }


    // ----------------- CONTROLLER ACTION -----------------


    /**Set the Max Number of players of the game
     * @param remoteController is the remoteController that will execute the action
     */
    @Override
    public void controlAction(RemoteController remoteController) {

        if (numberOfPlayers > 3 || numberOfPlayers <=1){
            String message = "Invalid number of players in game";
            remoteController.setResponse(new Nack(message, username, Command.PLAYERS));
        }
        else {
            executeAction(remoteController);
        }
    }
}
