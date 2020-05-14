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
    protected User user;



    /**Contains the different userData, used to pass all the infos about User: Username, age, IpaAddress,color etc..
     *
     * @author Veronica Motta
     * @return String
     */
    public String getUserData() {
        return userData;
    }

    public CliColor userColorAscii(){
        switch (user.getColor()){
            case CYAN:
                return  CliColor.CYAN;
            case GREEN:
                return CliColor.GREEN;
            case RED:
                return CliColor.RED;
            case BLUE:
                return CliColor.BLUE;
            case PURPLE:
                return CliColor.PURPLE;
            case YELLOW:
                return CliColor.YELLOW;
            default:
                return CliColor.RESET;
        }

    }

    public void setUser(User user) {
        this.user = user;
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
     * @return ModelGame
     */
    public ModelGame getModelGame() {
        return modelGame;
    }

    /**Use to get the updated list of available Color from the Controller
     *
     * @author Veronica Motta
     * @return List<ModelColor>
     */
    public List<ModelColor> getAvailableColor() {
        return availableColor;
    }

    /**Use to get the updated list of available Cell from the Controller
     *
     * @author Veronica Motta
     * @return List<Cell>
     */
    public List<Cell> getAvailableCell() {
        return availableCell;
    }

    /**Use to get the updated list of available God from the Controller
     *
     * @author Veronica Motta
     * @return List<God>
     */
    public List<God> getAvailableGod() {
        return availableGod;
    }

    /**Add as observer Client Controller
     *
     * @author Veronica Motta
     * @param controller
     */
    public void addController(ControllerClient controller){
        this.controllerClient=controller;
    }

    /**Use to set the command sent by the controller that will be used by the Cli and GUI methods
     *
     * @author Veronica Motta
     * @param command
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**Use to set the updated modelColor used to compare the color (sent by the controller) and the
     * CliColor that will be used by the Cli and GUI methods
     *
     * @author Veronica Motta
     * @param modelColor
     */
    public void setModelColor(ModelColor modelColor) {
        this.modelColor = modelColor;
    }

    /**Use to set the updated ModelGame that have all the infos about the game that will be used by the Cli and GUI methods
     *
     * @author Veronica Motta
     * @param modelGame
     */
    public void setModelGame(ModelGame modelGame) {this.modelGame = modelGame; }

    /**Use to set the updated list of available Color that will be used by the Cli and GUI methods
     *
     * @author Veronica Motta
     * @param availableColor
     */
    public void setAvailableColor(List<ModelColor> availableColor) {
        this.availableColor = availableColor;
    }

    /**Use to set the updated list of available Cell that will be used by the Cli and GUI methods
     *
     * @author Veronica Motta
     * @param availableCell
     */
    public void setAvailableCell(List<Cell> availableCell) {
        this.availableCell = availableCell;
    }

    /**Use to set the updated list of available God that will be used by the Cli and GUI methods
     *
     * @author Veronica Motta
     * @param availableGod
     */
    public void setAvailableGod(List<God> availableGod) {
        this.availableGod = availableGod;
    }


    abstract public void run();

    abstract public void printError(String message);
}
