package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelColor;
import it.polimi.ingsw.model.god.God;

import java.io.PrintWriter;
import java.util.Scanner;

/**Command Line Interface Class
 *
 * @author Veronica Motta
 */
public class Cli extends View {
    private static final int ROW = 5;
    private static final int COLUMN = 5;
    private final PrintWriter printWriter = new PrintWriter(System.out, true);
    private final Scanner scanner = new Scanner(System.in);
    private String scan;


    /**It start the game
     *
     */
    public Cli(){
        printLogo();
        this.command=Command.REGISTER;
        //run();

    }

    /**Called by Cli method to print game logo
     *
     */
    private void printLogo() {
        String Title = CliColor.TITLE + "\n" +
                "                .d88888b   .d888888  888888ba  d888888P  .88888.   888888ba  dP 888888ba  dP " +"\n" +
                "                88.       d8'    88  88    `8b    88    d8'   `8b  88    `8b 88 88    `8b 88 " +"\n" +
                "                `Y88888b. 88aaaaa88  88     88    88    88     88  a88aaaa8P'88 88     88 88 " +"\n" +
                "                      `8b 88     88  88     88    88    88     88  88   `8b. 88 88     88 88 " +"\n" +
                "                d8'   .8P 88     88  88     88    88    Y8.   .8P  88     88 88 88     88 88 " +"\n" +
                "                 Y88888P  88     88  dP     dP    dP     `8888P'   dP     dP dP dP     dP dP " +"\n" +
                "                ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +"\n" +
                "                                                                                             " +"\n\n" +
                "                                    Welcome to Santorini Board Game\n" +
                "                        Before starting playing you need to setup some things:\n" + CliColor.RESET;

        printWriter.println(Title);
    }

    /**Called by controller to print the board and compare the current cell with workerPosition and print the color of the user with the symbol
     * moreover compare the availableCells and print A if it's available and if the cell have height, print the level
     *
     */
    public void printBoard(){
        StringBuilder board = new StringBuilder();

        for(int i=0; i<ROW; i++){
            for(int j=0; j<COLUMN; j++){
                //board.append(CliColor.WHITE_BACKGROUND);
                board.append(" ");

                // If is there any worker
                if (modelGame.getWorkerListPosition().contains(new Cell(i, j))){
                    board.append(printWorker(modelGame.getWorkerFromPosition(new Cell(i, j)).getColor()));

                }
                else {
                    board.append("[ ");
                }

                board.append("|");

                if (availableCell.contains(new Cell(i, j))){
                    board.append("A");
                }
                else {
                    board.append(" ");
                }
                board.append("|") ;

                board.append(modelGame.getBoard().getCell(new Cell(i, j)).getHeight());
                board.append("]");
                board.append(CliColor.RESET);
                board.append("\t");
            }
            board.append("\n");
        }
        printWriter.println(board);
    }

    /**Called by printBoard methods to compare the modelColor with the CliColor and return the same color
     *
     * @param modelColor receives the worker color from modelColor
     * @return String that contains CliColor
     */
    public String printWorker(ModelColor modelColor) {
        if (modelGame!=null){
            switch (modelColor){
                case RED:
                    return "" + CliColor.RED_BACKGROUND + "[" + "\u2691";
                case GREEN:
                    return "" + CliColor.GREEN_BACKGROUND + "[" + "\u2691";
                case YELLOW:
                    return "" + CliColor.YELLOW_BACKGROUND + "[" + "\u2691";
                case BLUE:
                    return "" + CliColor.BLUE_BACKGROUND + "[" + "\u2691";
                case PURPLE:
                    return "" + CliColor.PURPLE_BACKGROUND + "[" + "\u2691";
                case CYAN:
                    return "" + CliColor.CYAN_BACKGROUND + "[" + "\u2691";
                default:
                    return "";
            }
        } else return "";

    }

    /**It check the command sent from the controller and then call the method, after notifyAll to the controller
     *
     */
    public void run(){
        switch (command) {
            case REGISTER:
                register();
                break;
            case COLOR:
                color();
                break;
            case GOD_LIST_THREE:
                godListThree();
                break;
            case GOD_LIST_TWO:
                godListTwo();
                break;
            case GOD:
                god();
                break;
            case SET_WORKER_POSITION:
                setWorkerPosition();
                break;
            case MOVE:
                moveWorker();
                break;
            case USE_GOD_POWER:
                usePower();
                break;
            case BUILD:
                build();
                break;
            case LOOSE:
                loose();
                break;
            case WIN:
                win();
                break;
            case PLAYERS:
                players();
                break;
            case QUIT:
                quit();
                break;
        }
        printWriter.println(userData);
        controllerClient.notifyControllerAction();
    }

    @Override
    public void printError(String message) {
        printWriter.println(message);
    }

    /**Called by run method it register data user
     *
     */
    public void register(){
        printWriter.println("Insert Username and Press Enter:\n");
        scan = scanner.nextLine();
        this.userData = "username=" + scan + ";";

        printWriter.println("Insert IpAddress and Press Enter:\n");
        scan = scanner.nextLine();
        userData += "address=" + scan + ";";

        printWriter.println("Insert age and Press Enter:\n");
        scan = scanner.nextLine();
        userData += "age=" + scan + ";";
    }

    /**Print the error that the name already exist
     *
     */
    public void usernameExist() {
        printWriter.println("Username already exist");
    }

    /**Called by run method to let the user choose the color in the available list color
     *
     */
    public void color(){
        printWriter.println("Choose a color from the fallowing list");
        for(ModelColor color : availableColor ){
            printWriter.println(color);
        }
        scan = scanner.nextLine();
        this.userData= "color=" + scan + ";";
    }

    /**Called by run method to let the user choose 3 gods in the available list god that can be choose from the other players
     *
     */
    public void godListThree(){
        for(God god : availableGod ){
            printWriter.println(god);
        }
        this.userData="";
        for(int i=0; i<3; i++){
            printWriter.println("Choose a god from the previous list:");
            scan = scanner.nextLine();
            userData="god=" + scan + ";";
        }
    }

    /**Called by run method to let the user choose 2 gods in the available list god that can be choose from the other players
     *
     */
    public void godListTwo(){
        for(God god : availableGod ){
            printWriter.println(god);
        }
        this.userData="";
        for(int i=0; i<2; i++){
            printWriter.println("Choose a god from the previous list:");
            scan = scanner.nextLine();
            userData+="god=" + scan + ";";
        }
    }

    /**Called by run method to let the user choose personal god in the available list god
     *
     */
    public void god(){
        this.userData="";
        printWriter.println("Choose a god from the fallowing list:");
        for(God god : availableGod ){
            printWriter.println(god);
        }
        scan = scanner.nextLine();
        userData="god="+ scan + ";";
    }

    /**Called by run method to let the user set the initial worker's position.
     * The worker is already selected at the beginning of the turn (usePower)and not here
     *
     */
    public void setWorkerPosition(){
        printWriter.println("Where you want to put your worker?");
        scan = scanner.nextLine();
        this.userData="workerPosition=" + scan + ";";
    }

    /**Called by run method to let the user choose where to put the worker.
     * The worker is already selected at the beginning of the turn (usePower)and not here
     */
    public void moveWorker(){
        printWriter.println("Where you want to put your worker?");
        scan = scanner.nextLine();
        this.userData="moveWorker=" + scan + ";";
    }

    /**Called by run method to let the user choose if he wants to activate the power of the god on a determinate worker.
     * It is called first and is here where the worker is chosen
     */
    public void usePower(){
        printWriter.println("Which worker?");
        scan = scanner.nextLine();
        this.userData= "worker="+ scan + ";";
        printWriter.println("Do you want to use your power?");
        scan = scanner.nextLine();
        userData+= "power=" + scan + ";";
    }

    /**Called by run method to let the user choose where he wants build
     *
     */
    public void build(){
        printWriter.println("Where do you want to build?");
        scan = scanner.nextLine();
        userData+= "build=" + scan + ";";
    }

    /**Called by run method if the user loose and print loose message
     *
     */
    public void loose(){
        printWriter.println("Sorry, you loose. Do you want to continue watching the game? Yes or No");
        scan = scanner.nextLine();
        this.userData= "watch=" + scan + ";";
    }

    /**Called by run method if the user win and print win message
     *
     */
    public void win(){
        printWriter.println("OH MAN, YOU WIN!");
    }

    /**Called by run method to let the user choose how many players can play in the game
     *
     */
    public void players(){
        printWriter.println("How many play?");
        scan = scanner.nextLine();
        this.userData="players=" + scan + ";";
    }

    /**Called by run method if the user quit the game and print quit message
     *
     */
    public void quit(){
        printWriter.println("You're quitting, you have done");
    }

}