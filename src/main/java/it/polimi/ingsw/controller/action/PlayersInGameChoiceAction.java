package it.polimi.ingsw.controller.action;

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
        this.className = getClass().getSimpleName();
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
     * @param maxPlayers is the number Of players
     */
    public void executeAction(int maxPlayers){
        maxPlayers = this.numberOfPlayers;
    }
}
