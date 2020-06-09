package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelColor;
import it.polimi.ingsw.model.User;
import it.polimi.ingsw.model.god.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static it.polimi.ingsw.model.ModelColor.*;
import static it.polimi.ingsw.model.ModelColor.YELLOW;

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

    // --------------- CONSTRUCTOR ---------------

    /**It start the game
     *
     */
    public Cli(){
        printLogo();
        this.command=Command.REGISTER;

    }

    // --------------- GETTER ----------------

    public static int getROW() {
        return ROW;
    }

    public static int getCOLUMN() {
        return COLUMN;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public String getScan() {
        return scan;
    }

    public CliColor getUserCliColor(User user){
        if (user.getColor() == null) return CliColor.RESET;
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

    // --------------- SETTER ----------------

    public void setScan(String scan) {
        this.scan = scan;
    }



    // --------------- PRINTER ---------------


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

        if (modelGame == null) return;
        StringBuilder board = new StringBuilder();

        for(int i=0; i<ROW; i++){
            if (i==0) {
                board.append(CliColor.CYAN + "      A \t\tB \t\t\tC \t\t\tD \t\t\tE\n" + CliColor.RESET );
                //board.append(CliColor.CYAN + "  -------------------------------------------------------\n" + CliColor.RESET);
            }
            for(int j=0; j<COLUMN; j++){
                //board.append(CliColor.WHITE_BACKGROUND);
                if(j==0 && i==0)
                    board.append(CliColor.CYAN + "" + i + " " + CliColor.RESET);
                if(j==0 && i==1)
                    board.append(CliColor.CYAN + "" + i + " " + CliColor.RESET);
                if(j==0 && i==2)
                    board.append(CliColor.CYAN + "" + i + " " + CliColor.RESET);
                if(j==0 && i==3)
                    board.append(CliColor.CYAN + "" + i + " " + CliColor.RESET);
                if(j==0 && i==4)
                    board.append(CliColor.CYAN + "" + i + " " + CliColor.RESET);

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
                    board.append("\u001B[1m" + "A");
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
        printWriter.println("\n" + board);
        printUser();
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



    public void printUser(){
        if (modelGame!= null){
            for(User user : modelGame.getUserList()){
                printWriter.println(getUserCliColor(user) + "" + user + CliColor.RESET);
            }
        }

    }


    // --------------- LOGIC -----------------

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
                return;
            case WIN:
                win();
                return;
            case PLAYERS:
                players();
                break;
            case QUIT:
                quit();
                return;
        }


        controllerClient.notifyControllerAction();
    }

    /**Print message error
     * @param message is the message that will be printed
     */
    @Override
    public void printError(String message) {
        printWriter.println(message);
    }

    /**Called by run method it register data user
     *
     */
    public void register(){
        printWriter.println(CliColor.CYAN + "Insert Username and Press Enter:");
        boolean match = true;
        scan = scanner.nextLine();



        if(!scan.matches("[a-zA-Z]+")){
            match = false;
        }
        this.userData = "username=" + scan + ";";

        printWriter.println("Insert IpAddress and Press Enter:");
        scan = scanner.nextLine();

        if (!scan.equals("localhost") && !scan.matches("[0-9]+[.][0-9]+[.][0-9]+[.][0-9]+")) match = false;

        userData += "address=" + scan + ";";

        printWriter.println("Insert age and Press Enter:" + CliColor.RESET);
        scan = scanner.nextLine();

        if (!scan.matches("[0-9]+")) match = false;

        userData += "age=" + scan + ";";

        if (!match) register();

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

        printWriter.println(CliColor.CYAN + "Choose a color from the following list:" + CliColor.RESET);
        for(ModelColor color : availableColor ){
            String print = "";
            switch (color){
                case RED:
                    print = "" + CliColor.RED;
                    break;
                case GREEN:
                    print = "" + CliColor.GREEN;
                    break;
                case YELLOW:
                    print = "" + CliColor.YELLOW;
                    break;
                case BLUE:
                    print = "" + CliColor.BLUE;
                    break;
                case PURPLE:
                    print = "" + CliColor.PURPLE;
                    break;
                case CYAN:
                    print = "" + CliColor.CYAN;
                    break;
            }

            printWriter.println(print + availableColor.indexOf(color) + " - " + color + CliColor.RESET);

        }
        scan = scanner.nextLine();
        this.userData= "color=" + scan + ";";

        if (!scan.matches("[0-9]+")) color();
    }

    /**Called by run method to let the user choose 3 gods in the available list god that can be choose from the other players
     *
     */
    public void godListThree(){
        boolean match = true;
        for(God god : availableGod ){
            printWriter.println(availableGod.indexOf(god) + " - " + god + CliColor.RESET);
        }
        this.userData="";
        for(int i=0; i<3; i++){
            printWriter.println(CliColor.CYAN + "\n" + "Choose a god from the previous list:" + CliColor.RESET);
            scan = scanner.nextLine();

            if(!scan.matches("[0-9]+")) match = false;

            userData += "god=" + scan + ";";
        }
        if (!match) godListThree();
    }

    /**Called by run method to let the user choose 2 gods in the available list god that can be choose from the other players
     *
     */
    public void godListTwo(){
        boolean match = true;
        for(God god : availableGod ){
            printWriter.println(availableGod.indexOf(god) + " - " + god + CliColor.RESET);
        }
        this.userData="";
        for(int i=0; i<2; i++){
            printWriter.println(CliColor.CYAN  + "Choose a god from the previous list:" + CliColor.RESET);
            scan = scanner.nextLine();

            if(!scan.matches("[0-9]+")) match = false;

            userData += "god=" + scan + ";";
        }
        if (!match) godListTwo();
    }

    /**Called by run method to let the user choose personal god in the available list god
     *
     */
    public void god(){
        this.userData="";
        printWriter.println(CliColor.CYAN +  "Choose a god from the following list:" + CliColor.RESET);
        for(God god : availableGod ){
            printWriter.print(CliColor.CYAN);
            printWriter.println(availableGod.indexOf(god) + " - " + god + CliColor.RESET);
        }
        scan = scanner.nextLine();

        userData="god="+ scan + ";";
        if(!scan.matches("[0-9]+")) god();
    }


    /**Called by run method to let the user set the initial worker's position.
     * The worker is already selected at the beginning of the turn (usePower)and not here
     *
     */
    public void setWorkerPosition(){
        printWriter.println(CliColor.CYAN +  "Where do you want to put your worker?" + CliColor.RESET);
        scan = scanner.nextLine();

        this.userData="workerPosition=" + scan + ";";

        if(!(scan.matches("[0-9][,-.:]*[a-zA-Z]") || scan.matches("[a-zA-Z][,-.:]*[0-9]")))
            setWorkerPosition();
    }

    /**Called by run method to let the user choose where to put the worker.
     * The worker is already selected at the beginning of the turn (usePower)and not here
     */
    public void moveWorker(){
        printWriter.println(CliColor.CYAN  + "Where do you want to move your worker?" + CliColor.RESET);
        scan = scanner.nextLine();

        this.userData="moveWorker=" + scan + ";";

        if(!(scan.matches("[0-9][,-.:]*[a-zA-Z]") || scan.matches("[a-zA-Z][,-.:]*[0-9]")))
        moveWorker();
    }

    /**Called by run method to let the user choose if he wants to activate the power of the god on a determinate worker.
     * It is called first and is here where the worker is chosen
     */
    public void usePower(){
        boolean match = true;
        printWriter.println(CliColor.CYAN + "Which worker?");
        scan = scanner.nextLine();

        if(!(scan.matches("[0-9][,-.:]*[a-zA-Z]") || scan.matches("[a-zA-Z][,-.:]*[0-9]")))
            match = false;

        this.userData= "worker="+ scan + ";";


        printWriter.println("Do you want to use your power?" + CliColor.RESET);
        scan = scanner.nextLine();

        if(!scan.matches("[a-zA-Z]+")) match = false;

        userData+= "power=" + scan + ";";

        if (!match) usePower();
    }

    /**Called by run method to let the user choose where he wants build
     *
     */
    public void build(){
        printWriter.println(CliColor.CYAN + "Where do you want to build?" + CliColor.RESET);
        scan = scanner.nextLine();
        userData= "build=" + scan + ";";
        if(!(scan.matches("[0-9][,-.:]*[a-zA-Z]") || scan.matches("[a-zA-Z][,-.:]*[0-9]")))
            build();
    }

    /**Called by run method if the user loose and print loose message
     *
     */
    public void loose(){
        printWriter.println(CliColor.CYAN +  "Sorry, you lose" +CliColor.RESET);
    }

    /**Called by run method if the user win and print win message
     *
     */
    public void win(){
        printWriter.println(CliColor.CYAN +  "OH MAN, YOU WIN!" + CliColor.RESET);
    }

    /**Called by run method to let the user choose how many players can play in the game
     *
     */
    public void players(){
        printWriter.println(CliColor.CYAN + "How many players?" + CliColor.RESET);
        scan = scanner.nextLine();

        this.userData="players=" + scan + ";";
        if(!scan.matches("[0-9]")) players();

    }

    /**Called by run method if the user quit the game and print quit message
     *
     */
    public void quit(){
        printWriter.println(CliColor.CYAN  + "You're quitting, you have done" + CliColor.RESET);
    }




}