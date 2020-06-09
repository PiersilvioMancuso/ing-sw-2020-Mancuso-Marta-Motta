package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ControllerClient;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelColor;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.model.god.God;

import java.io.Serializable;
import java.util.List;

import static it.polimi.ingsw.model.ModelColor.*;

/**It is used to select Cli or GUI view, it set the common variable
 *
 * @author Piersilvio Mancuso
 */
abstract public class View implements Serializable {
    protected String userData;
    protected ControllerClient controllerClient;

    protected List<Cell> availableCell;
    protected List<God> availableGod;
    protected List<ModelColor> availableColor;
    protected ModelGame modelGame;
    public Command command;
    protected ModelColor modelColor;



    /**Contains the different userData, used to pass all the infos about User: Username, age, IpaAddress,color etc..
     *
     * @author Veronica Motta
     * @return String
     */
    public String getUserData() {
        return userData;
    }



    /**
     * @author Veronica Motta
     * @return ControllerClient
     */
    public ControllerClient getControllerClient() {
        return controllerClient;
    }

    /**Use to get the command from the Controller
     *
     * @author Veronica Motta
     * @return Command
     */
    public Command getCommand(){
        return command;
    }

    /**Use to get the updated ModelColor from the Controller
     *
     * @author Veronica Motta
     * @return ModelColor
     */
    public ModelColor getModelColor() {
        return modelColor;
    }

    /**Use to get the updated ModelGame from the Controller
     *
     * @author Veronica Motta
     * @return  the copy of ModelGame
     */
    public ModelGame getModelGame() {
        return modelGame;
    }

    /**Use to get the updated list of available Color from the Controller
     *
     * @author Veronica Motta
     * @return  a list of all available colors
     */
    public List<ModelColor> getAvailableColor() {
        return availableColor;
    }

    /**Use to get the updated list of available Cell from the Controller
     *
     * @author Veronica Motta
     * @return  a List of all available cells
     */
    public List<Cell> getAvailableCell() {
        return availableCell;
    }

    /**Use to get the updated list of available God from the Controller
     *
     * @author Veronica Motta
     * @return  a List of all available gods
     */
    public List<God> getAvailableGod() {
        return availableGod;
    }

    /**Add as observer Client Controller
     *
     * @author Veronica Motta
     * @param controller is the controllerClient thanks which View execute actions
     */
    public void addController(ControllerClient controller){
        this.controllerClient=controller;
    }

    /**Use to set the command sent by the controller that will be used by the Cli and GUI methods
     *
     * @author Veronica Motta
     * @param command is the command that has been received by the Server
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**Use to set the updated modelColor used to compare the color (sent by the controller) and the
     * CliColor that will be used by the Cli and GUI methods
     *
     * @author Veronica Motta
     * @param modelColor is the modelColor
     */
    public void setModelColor(ModelColor modelColor) {
        this.modelColor = modelColor;
    }

    /**Use to set the updated ModelGame that have all the infos about the game that will be used by the Cli and GUI methods
     *
     * @author Veronica Motta
     * @param modelGame is a copy of the model
     */
    public void setModelGame(ModelGame modelGame) {this.modelGame = modelGame ; }

    /**Use to set the updated list of available Color that will be used by the Cli and GUI methods
     *
     * @author Veronica Motta
     * @param availableColor is a list of all available colors, obtained by the Server
     */
    public void setAvailableColor(List<ModelColor> availableColor) {
        this.availableColor = availableColor;
    }

    /**Use to set the updated list of available Cell that will be used by the Cli and GUI methods
     *
     * @author Veronica Motta
     * @param availableCell is a list of all available cells, obtained by the Server
     */
    public void setAvailableCell(List<Cell> availableCell) {
        this.availableCell = availableCell;
    }

    /**Use to set the updated list of available God that will be used by the Cli and GUI methods
     *
     * @author Veronica Motta
     * @param availableGod is a list of available gods, obtained by the server
     */
    public void setAvailableGod(List<God> availableGod) {
        this.availableGod = availableGod;
    }


    /**Run the players' turn
     */
    abstract public void run();

    /**Print the board
     */
    abstract public void printBoard();

    /**Print the Errors
     * @param message is the errorMessage that has to be displayed
     */
    abstract public void printError(String message);
}
