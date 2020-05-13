package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.RemoteController;

/**Players In Game Choice Action
 * @author Piersilvio Mancuso
 */
public class PlayersInGameChoiceAction extends Action{

    private int numberOfPlayers;

    // ---------------- CONSTRUCTOR ---------------

    /**PlayersInGameConstructor
     * @param string is the string that will be analyzed to set the Action data
     */
    public PlayersInGameChoiceAction(String string){
        super();
        System.out.println(string);
        this.className = "PlayersInGameChoiceAction";
        System.out.println(string);
        String[] message = string.split(";");
        this.username = message[0].split("=")[1];
        this.numberOfPlayers = Integer.parseInt(message[1].split("=")[1]);
        System.out.println(numberOfPlayers);
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
}
