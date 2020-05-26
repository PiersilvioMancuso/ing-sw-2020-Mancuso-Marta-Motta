package it.polimi.ingsw.view;

import it.polimi.ingsw.model.ModelColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class CellButton extends JButton {
    int row;
    int column;
    List<Icon> iconList;
    Color color;
    
    public CellButton(int row, int column) {
        this.iconList = new ArrayList<>();
        this.iconList.add(new ImageIcon("level1.jpg"));
        this.iconList.add(new ImageIcon("level2.jpg"));
        this.iconList.add(new ImageIcon("level3.jpg"));
        this.iconList.add(new ImageIcon("level4.jpg"));
        //TODO: Insert list of icons

        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    
    public void setButtonColor(ModelColor color){
        this.color = Color.getColor(color.getMessage().toUpperCase());
    }

    public void addListener(){
        //TODO: Set the effect for button pressure
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Gui.userData = "cell=" + row +"," + (column + 'A') + ";";
            }
        });
    }

    public Color getColor() {
        return color;
    }

    public void setHeight(int height){
        if (height > 0 && height <= 4){
            this.setIcon(iconList.get(height - 1));
        }
    }
}
