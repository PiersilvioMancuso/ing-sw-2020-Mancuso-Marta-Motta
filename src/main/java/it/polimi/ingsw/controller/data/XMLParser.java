package it.polimi.ingsw.controller.data;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.Apollo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * XML Parser
 * @author Mattia Marta
 */
public class XMLParser {

    /**
     * Converts a Cell Object into a StringList.
     * @param cell is the cell to convert.
     * @return the XML block containing the cell infos.
     */
    public static List<String> cellToStringList(Cell cell){

        List<String> cellString = new ArrayList<>();
        String head = "<cell>\n";
        String tail = "</cell>\n";
        cellString.add(head);
        cellString.add("<posX>" + cell.getRow() + "</posX>\n");
        cellString.add("<posY>" + cell.getColumn() + "</posY>\n");
        cellString.add("<height>" + cell.getHeight() + "</height>\n");
        cellString.add(tail);
        return cellString;
    }

    /**
     * Convert the list of cells to a StringList.
     * @param cellList is the List containing all the cells.
     * @return the XML blocks containing all the cell infos.
     */
    public static List<String> cellListToStringList(List<Cell> cellList){
        List<String> cellListStrings = new ArrayList<>();
        String head = "<cellList>\n";
        String tail = "</cellList>\n";

        cellListStrings.add(head);
        for (Cell cell : cellList){
            cellListStrings.addAll(cellToStringList(cell));
        }
        cellListStrings.add(tail);
        return cellListStrings;
    }

    /**
     * Convert the board to a StringList.
     * @param board is the board of the game.
     * @return the XML block containing the board infos.
     */
    public static List<String> boardToStringList(Board board){
        List<String> boardStrings = new ArrayList<>();
        String head = "<board>\n";
        String tail = "</board>\n";

        boardStrings.add(head);
        boardStrings.addAll(cellListToStringList(board.getBuildMap()));
        boardStrings.add(tail);

        return boardStrings;
    }

    /**
     * Return a List of valid cells.
     * @param cellList is the list of valid cells.
     * @return the XML block containing the valid cells.
     */
    public static List<String> validCellsToStringList(List<Cell> cellList){
        List<String> validCells = new ArrayList<>();
        String head = "<validCells>\n";
        String tail = "</validCells>\n";

        validCells.add(head);
        validCells.addAll(cellListToStringList(cellList));
        validCells.add(tail);

        return validCells;
    }

    /**
     * Convert the user to a StringList.
     * @param user is the user to convert.
     * @return the XML block containing the user's infos.
     */
    public static List<String> userToStringList(User user){
        List<String> userString = new ArrayList<>();
        String head = "<user>\n";
        String tail = "</user>\n";

        userString.add(head);
        userString.add("<username>" + user.getUsername() + "</username>\n");
        userString.add("<age>" + user.getAge() + "</age>\n");
        userString.add("<god>" + user.getGod().getClass().getSimpleName() + "</god>\n");
        userString.add("<outcome>" + user.getOutCome() + "</outcome>\n");
        userString.add("<color>" + user.getColor() + "</color>\n");
        userString.add(tail);

        return userString;
    }

    /**
     * Convert the userList to a StringList.
     * @param userList is the user to convert.
     * @return the XML block containing the users' infos.
     */
    public static List<String> userListToStringList(List<User> userList){
        List<String> userListString = new ArrayList<>();
        String head = "<userList>\n";
        String tail = "</userList>\n";

        userListString.add(head);
        for (User user : userList){
            userListString.addAll(userToStringList(user));
        }

        userListString.add(tail);
        return userListString;
    }

    /**
     * Convert current user to String.
     * @param currentUser is the current user.
     * @return the XML block containing the current user.
     */
    public static String currentUserToString(int currentUser){
        return "<currentUser>" + currentUser + "</currentUser>\n";
    }

    /**
     * Convert the worker to a StringList.
     * @param worker is the Worker to convert.
     * @return the XML block containing the worker's infos.
     */
    public static List<String> workerToStringList(Worker worker){
        List<String> workerString = new ArrayList<>();
        String head = "<worker>\n";
        String tail = "</worker>\n";

        workerString.add(head);
        workerString.add("<user>" + worker.getUser().getUsername() + "</user>\n");
        workerString.addAll(cellToStringList(worker.getPosition()));
        workerString.add(tail);

        return workerString;
    }

    /**
     * Convert the worker list to a StringList.
     * @param workerList is the list of workers.
     * @return the XML block containing the workers' infos.
     */
    public static List<String> workerListToStringList(List<Worker> workerList){
        List<String> workerListString = new ArrayList<>();
        String head = "<workerList>\n";
        String tail = "</workerList>\n";

        workerListString.add(head);

        for (Worker worker : workerList){
            workerListString.addAll(workerToStringList(worker));
        }

        workerListString.add(tail);

        return workerListString;
    }

    /**
     * Convert the model to string list.
     * @param modelGame is the model of the game.
     * @return the XML block containing the infos of the model.
     */
    public static List<String> modelToString(ModelGame modelGame){
        List<String> modelToStringList = new ArrayList<>();
        String head = "<model>\n";
        String tail = "</model>\n";
        modelToStringList.add("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        modelToStringList.add(head);

        modelToStringList.addAll(boardToStringList(modelGame.getBoard()));
        modelToStringList.addAll(validCellsToStringList(modelGame.getValidCells()));
        modelToStringList.addAll(userListToStringList(modelGame.getUserList()));
        modelToStringList.add(currentUserToString(modelGame.getCurrentUserIndex()));
        modelToStringList.addAll(workerListToStringList(modelGame.getWorkerList()));

        modelToStringList.add(tail);
        return modelToStringList;
    }

    /**
     * Save the converted model game to an XML file.
     * @param modelGame is the model of the game.
     * @param file is the file where to write.
     */
    public static void saveModel(ModelGame modelGame, File file){
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            List<String> modelString = modelToString(modelGame);

            for (String string : modelString){
                printWriter.print(string);
            }

            printWriter.flush();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
