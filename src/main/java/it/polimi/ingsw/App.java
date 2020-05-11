package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.model.messages.GodEnum;
import it.polimi.ingsw.view.Cli;
import it.polimi.ingsw.view.Command;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App
{
    public void printHelo(){
        System.out.println("Helo");
    }

    public static void clearScreen() {

        System.out.print("\033[H" + "\033[2J");

        System.out.flush();

    }

    public static void clrscr(){

        //Clears Screen in java

        try {

            if (System.getProperty("os.name").contains("Windows"))

                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            else

                Runtime.getRuntime().exec("clear");

        } catch (IOException | InterruptedException ex) {}

    }

    public static void main( String[] args ) throws InterruptedException, IOException {
        App app = new App();
        PrintWriter printWriter = new PrintWriter(System.out, true);

        for (int i = 0; i < 10; i++){
            printWriter.println(i);
        }


        Thread.sleep(1500);
        App.clearScreen();
        //App.clrscr();

        printWriter = new PrintWriter(System.out, true);
        printWriter.println(100);


    }
}
