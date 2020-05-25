package it.polimi.ingsw.controller.utility;

import it.polimi.ingsw.model.Cell;

import java.io.IOException;

public class Converter {

    public static Cell convertCell(String message){
        String cellString = message.split("=")[1];
        String regex = "[+-,;'*.^-]*";
        cellString = cellString.replaceAll(regex, "").toUpperCase();

        if (cellString.matches("^[A-Z]"))
            return new Cell(cellString.charAt(0) - 'A', Integer.parseInt(cellString.substring(1))%10);
        else
            return new Cell(Integer.parseInt(cellString.substring(0,1))%10, cellString.charAt(1) - 'A' );
    }
    
}
