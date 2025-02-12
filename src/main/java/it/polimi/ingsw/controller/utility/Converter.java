package it.polimi.ingsw.controller.utility;

import it.polimi.ingsw.model.Cell;

import java.io.IOException;

/**Converter Class
 * @author Mattia Marta
 */
public class Converter {

    /**Convert the input message into a Cell
     * @param message is the message that will contains data for Cell creation
     * @return the Cell converted by message
     */
    public static Cell convertCell(String message){
        String cellString = message.split("=")[1];
        String regex = "[+-,;'*.^-]*";
        cellString = cellString.replaceAll(regex, "").toUpperCase();

        if (cellString.matches("[A-Z][0-9]")){
            return new Cell(Integer.parseInt(cellString.substring(1))%10 ,cellString.charAt(0) - 'A');
        }

        else
            return new Cell(Integer.parseInt(cellString.substring(0,1))%10, cellString.charAt(1) - 'A' );
    }
    
}
