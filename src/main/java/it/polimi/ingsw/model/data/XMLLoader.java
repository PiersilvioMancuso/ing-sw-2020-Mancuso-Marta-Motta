package it.polimi.ingsw.model.data;

import it.polimi.ingsw.messages.GodEnum;
import it.polimi.ingsw.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class XMLLoader {

    public static String XMLTagRemover(String string){
        string = string.replaceAll("[<][/]?[a-zA-Z]+[>]", "");
        return string;
    }

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

    public static List<String> getXMLTagDefinition(List<String> strings, String tag){
        List<String> res = new ArrayList<>();
        String headTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";

        int headTagIndex = 0, endTagIndex = 0;
        for (String string : strings){
            if (string.contains(headTag)) headTagIndex = strings.indexOf(string);
            if (string.contains(endTag)) endTagIndex = strings.indexOf(string);
        }

        if (headTagIndex != endTagIndex)
            res = strings.subList(headTagIndex, endTagIndex + 1);
        else
            res.add(strings.get(headTagIndex));
        return res;
    }

    public static Cell cellCreator(List<String> stringList){
        List<String> cellStringList = getXMLTagDefinition(stringList, "cell");
        Cell cell = new Cell();
        List<String> removedTagCellStringList = new ArrayList<>();
        for (String string : cellStringList){
            string =XMLTagRemover(string);
            if (!string.equals("")) removedTagCellStringList.add(string);
        }
        cell.setX(Integer.parseInt(removedTagCellStringList.get(0)));
        cell.setY(Integer.parseInt(removedTagCellStringList.get(1)));
        cell.setHeight(Integer.parseInt(removedTagCellStringList.get(2)));

        return cell;
    }

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

    public static List<Cell> validCellsCreator(List<String> stringList){
        List<String> validCellsStringList = getXMLTagDefinition(stringList, "validCells");
        return cellListCreator(validCellsStringList);
    }

    public static Board boardCreator(List<String> stringList){
        List<String> boardStringList = getXMLTagDefinition(stringList, "board");
        Board board = new Board();
        board.setBuildMap(cellListCreator(boardStringList));

        return board;
    }

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
        user.setGod(GodEnum.valueOf(userFieldsStringList.get(2).toUpperCase()).getGod());
        user.setOutCome(OutCome.valueOf(userFieldsStringList.get(3)));
        user.setColor(ModelColor.valueOf(userFieldsStringList.get(4)));

        return user;
    }

    public static List<User> userListCreator(List<String> stringList){
        List<User> userList = new ArrayList<>();
        List<String> userStringList = getXMLTagDefinition(stringList, "userList");

        for (int i = 1; i < userStringList.size() - 1; i = i + 7){
            User user = userCreator(userStringList.subList(i, i + 7));
            userList.add(user);
        }

        return userList;
    }

    public static Worker workerCreator(List<String> stringList, List<User> userList){
        List<String> workerStringList = getXMLTagDefinition(stringList, "worker");
        String username = workerStringList.get(1);
        int i = 0;

        for (User user : userList){
            if (user.getUsername().equals(username)) i = userList.indexOf(user);
        }
        Worker worker = new Worker(userList.get(i));
        worker.setPosition(cellCreator(stringList.subList(2,7)));
        return worker;
    }


    public static List<Worker> workerListCreator(List<String> stringList, List<User> userList){
        List<String> workerListStringList = getXMLTagDefinition(stringList, "workerList");
        List<Worker> workerList = new ArrayList<>();

        for (int i = 1; i < workerListStringList.size()-1; i = i + 8){
            workerList.add(workerCreator(workerListStringList.subList(i, i + 8), userList));
        }

        return workerList;
    }


    public static int currentUserCreator(List<String> stringList){
        String currentUserString = getXMLTagDefinition(stringList, "currentUser").get(0);
        currentUserString = XMLTagRemover(currentUserString);
        return Integer.parseInt(currentUserString);
    }

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


    public static void main(String[] args) {
        String path = System.getProperty("user.dir");

        System.out.println(GodEnum.valueOf("Apollo".toUpperCase()).getGod());


        String contextPath = "/src/main/java/it/polimi/ingsw/model/data/";
        String fileName = "model.xml";
        File file = new File(path+contextPath+fileName);

        System.out.println(getXMLTagDefinition(XMLtoStringList(file), "validCells"));
    }

}
