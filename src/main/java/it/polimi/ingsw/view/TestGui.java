package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestGui {

    public static void main(String[] args) {
        ModelGame modelGame = new ModelGame();

        User user = new User("CiaoTest");
        user.setColor(ModelColor.RED);
        Board board = new Board();

        board.setCellBoard(new Cell(2,3,2));

        modelGame.setBoardGame(board);
        modelGame.addUser(user);
        modelGame.addWorker(new Worker(user));
        modelGame.getWorkerList().get(0).setPosition(new Cell(2,3));

        List<Cell> cellList = new ArrayList<>();
        cellList.add(new Cell(1,2,3));

        JFrame frame = new JFrame("Santorini");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(true);



        Container centerLayout = new Container();
        centerLayout.setLayout(new BorderLayout());

        frame.setContentPane(centerLayout);

        JPanel panel1 = new JPanel();

        Gui.printBoard(panel1, modelGame, cellList);
        centerLayout.add(panel1, BorderLayout.CENTER);
        panel1.setVisible(true);


        centerLayout.setVisible(true);

        //frame.add(centerLayout);
        frame.setVisible(true);

    }
}
