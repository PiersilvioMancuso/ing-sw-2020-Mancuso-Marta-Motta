package it.polimi.ingsw.controller.data;

import it.polimi.ingsw.messages.GodEnum;
import it.polimi.ingsw.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * XML Loader Class
 * @author Mattia Marta
 */
public class XMLLoader {

    /**
     * Remove the XML Tags
     * @param string is the string received
     * @return a clean string
     */
    public static String XMLTagRemover(String string){
        string = string.replaceAll("[<][/]?[a-zA-Z]+[>]", "");
        return string;
    }

    /**
     * Remove XML Tags.
     * @param file is the XML file taken as input.
     * @return a string of the whole file without tags
     */
    public static List<String> XMLtoStringList(File file){
        List<String> XMLStringList = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader(file));


            while (scanner.hasNext()){
                XMLStringList.add(scanner.nextLine());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return XMLStringList;
    }

    /**
     * Get the single tag content.
     * @param strings is the string passed.
     * @param tag is the tag searched.
     * @return the tag definition.
     */
    public static List<String> getXMLTagDefinition(List<String> strings, String tag){
        List<String> res = new ArrayList<>();
        String headTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";

        int headTagIndex = 0, endTagIndex = 0;
        for (String string : strings){
            if (string.contains(headTag)) headTagIndex = strings.indexOf(string);
            if (string.contains(endTag)) endTagIndex = strings.indexOf(string);
        }
        if (headTagIndex != endTagIndex ) res = strings.subList(headTagIndex, endTagIndex + 1);
        else res.add(strings.get(headTagIndex));
        return res;
    }

    /**
     * Create the cell from the string list.
     * @param stringList is the loaded XML file.
     * @return a Cell object.
     */
    public static Cell cellCreator(List<String> stringList){
        List<String> cellStringList = getXMLTagDefinition(stringList, "cell");
        Cell cell = new Cell();
        List<String> removedTagCellStringList = new ArrayList<>();
        for (String string : cellStringList){
            string =XMLTagRemover(string);
            if (!string.equals("")) removedTagCellStringList.add(string);
        }
        cell.setRow(Integer.parseInt(removedTagCellStringList.get(0)));
        cell.setColumn(Integer.parseInt(removedTagCellStringList.get(1)));
        cell.setHeight(Integer.parseInt(removedTagCellStringList.get(2)));

        return cell;
    }

    /**
     * Create a list of cells.
     * @param stringList is the loaded XML file.
     * @return a list of Cells.
     */
    public static List<Cell> cellListCreator(List<String> stringList){
        List<String> cellListStringList = getXMLTagDefinition(stringList, "cellList");
        List<Cell> cellList = new ArrayList<>();

        for (int i = 1; i < cellListStringList.size() - 1; i = i + 5){
            List<String> cellString = cellListStringList.subList(i, i + 5);
            Cell cell = cellCreator(cellString);
            cellList.add(cell);
        }

        return cellList;
    }

    /**
     * Create a list of valid cells.
     * @param stringList is the loaded XML file.
     * @return the list of valid cells.
     */
    public static List<Cell> validCellsCreator(List<String> stringList){
        List<String> validCellsStringList = getXMLTagDefinition(stringList, "validCells");
        return cellListCreator(validCellsStringList);
    }

    /**
     * Create the board from the Loaded XML.
     * @param stringList is the loaded XML file.
     * @return a board object.
     */
    public static Board boardCreator(List<String> stringList){
        List<String> boardStringList = getXMLTagDefinition(stringList, "board");
        Board board = new Board();
        board.setBuildMap(cellListCreator(boardStringList));

        return board;
    }

    /**
     * Create the user from the Loaded XML.
     * @param stringList is the loaded XML file.
     * @return a user object.
     */
    public static User userCreator(List<String> stringList){
        List<String> userStringList = getXMLTagDefinition(stringList, "user");
        List<String> userFieldsStringList = new ArrayList<>();
        User user = new User();

        for (String string : userStringList){
            string = XMLTagRemover(string);
            if (!string.equals("")) userFieldsStringList.add(string);
        }
        user.setUsername(userFieldsStringList.get(0));
        user.setAge(Integer.parseInt(userFieldsStringList.get(1)));
        user.setGodChosen(GodEnum.valueOf(userFieldsStringList.get(2).toUpperCase()).getGod());
        user.setOutCome(OutCome.valueOf(userFieldsStringList.get(3)));
        user.setColor(ModelColor.valueOf(userFieldsStringList.get(4)));

        return user;
    }

    /**
     * Create the user list from the loaded XML.
     * @param stringList is the loaded XML file.
     * @return a userList object.
     */
    public static List<User> userListCreator(List<String> stringList){
        List<User> userList = new ArrayList<>();
        List<String> userStringList = getXMLTagDefinition(stringList, "userList");

        for (int i = 1; i < userStringList.size() - 1; i = i + 7){
            User user = userCreator(userStringList.subList(i, i + 7));
            userList.add(user);
        }

        return userList;
    }

    /**
     * Create a worker from the loaded XML.
     * @param stringList is the loaded XML file.
     * @param userList is the list of users.
     * @return a worker object.
     */
    public static Worker workerCreator(List<String> stringList, List<User> userList){
        List<String> workerStringList = getXMLTagDefinition(stringList, "worker");
        String username = XMLTagRemover(workerStringList.get(1)) ;
        int i = 0;

        for (User user : userList){
            if (user.getUsername().equals(username)) i = userList.indexOf(user);
        }
        Worker worker = new Worker(userList.get(i));
        worker.setPosition(cellCreator(stringList.subList(2,7)));
        return worker;
    }

    /**
     * Create a worker list from te loaded XML.
     * @param stringList is the loaded XML file.
     * @param userList is the user List.
     * @return a workerList object.
     */
    public static List<Worker> workerListCreator(List<String> stringList, List<User> userList){
        List<String> workerListStringList = getXMLTagDefinition(stringList, "workerList");
        List<Worker> workerList = new ArrayList<>();

        for (int i = 1; i < workerListStringList.size()-1; i = i + 8){
            workerList.add(workerCreator(workerListStringList.subList(i, i + 8), userList));
        }

        return workerList;
    }

    /**
     * Create the current user from the loaded XML.
     * @param stringList is the loaded XML file.
     * @return the int of the current user.
     */
    public static int currentUserCreator(List<String> stringList){
        String currentUserString = getXMLTagDefinition(stringList, "currentUser").get(0);
        currentUserString = XMLTagRemover(currentUserString);
        return Integer.parseInt(currentUserString);
    }

    /**
     * Create the model of the game.
     * @param file is the XML file to load.
     * @return the modelGame object.
     */
    public static ModelGame modelGameCreator(File file){
        ModelGame modelGame = new ModelGame();
        List<String> stringList = XMLtoStringList(file);
        modelGame.setValidCells(validCellsCreator(stringList));
        modelGame.setUserList(userListCreator(stringList));
        modelGame.setBoardGame(boardCreator(stringList));
        modelGame.setCurrentUser(currentUserCreator(stringList));
        modelGame.setWorkerList(workerListCreator(stringList, modelGame.getUserList()));


        return modelGame;
    }


}
