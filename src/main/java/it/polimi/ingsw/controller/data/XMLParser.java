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

public class XMLParser {


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

    public static List<String> boardToStringList(Board board){
        List<String> boardStrings = new ArrayList<>();
        String head = "<board>\n";
        String tail = "</board>\n";

        boardStrings.add(head);
        boardStrings.addAll(cellListToStringList(board.getBuildMap()));
        boardStrings.add(tail);

        return boardStrings;
    }

    public static List<String> validCellsToStringList(List<Cell> cellList){
        List<String> validCells = new ArrayList<>();
        String head = "<validCells>\n";
        String tail = "</validCells>\n";

        validCells.add(head);
        validCells.addAll(cellListToStringList(cellList));
        validCells.add(tail);

        return validCells;
    }

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

    public static String currentUserToString(int currentUser){
        return "<currentUser>" + currentUser + "</currentUser>\n";
    }

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

    public static void main(String[] args) {
        String path = System.getProperty("user.dir");


        String contextPath = "/src/main/java/it/polimi/ingsw/model/data/";
        String fileName = "it/polimi/ingsw/controller/data/model.xml";
        File file = new File(path+contextPath+fileName);

        ModelGame modelGame = new ModelGame();
        User user = new User("Piersilvio");
        User user1 = new User("Tia");
        User user2 = new User("Vero");

        user.setGodChosen(new Apollo());
        user.setAge(23);
        user.setOutCome(OutCome.DRAW);
        user.setColor(ModelColor.GREEN);

        user1.setGodChosen(new Apollo());
        user1.setAge(23);
        user1.setOutCome(OutCome.DRAW);
        user1.setColor(ModelColor.GREEN);

        user2.setGodChosen(new Apollo());
        user2.setAge(23);
        user2.setOutCome(OutCome.DRAW);
        user2.setColor(ModelColor.GREEN);

        modelGame.addUser(user);
        modelGame.addUser(user1);
        modelGame.addUser(user2);

        Random random = new Random();
        for (User users : modelGame.getUserList()){
            for (int i = 0; i< 2; i++){
                Worker worker = new Worker(users);
                worker.setPosition(new Cell(random.nextInt() % 4, random.nextInt()%4));
                modelGame.addWorker(worker);
            }
        }

        modelGame.startGame();

        List<Cell> cellList = new ArrayList<>();
        for (int i = 0; i < 6; i++){
            cellList.add(new Cell(random.nextInt()%4, random.nextInt()%4));
        }

        modelGame.setValidCells(cellList);

        XMLParser.saveModel(modelGame, file);

        ModelGame newModelGame = XMLLoader.modelGameCreator(file);


        System.out.println(newModelGame);
    }

}
