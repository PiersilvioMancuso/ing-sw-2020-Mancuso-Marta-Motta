package it.polimi.ingsw.view;



import javax.swing.*;

public class Gui{
    private final JFrame frame;
    private JPanel panel;

    private final JLabel infoUser;
    private ImageIcon background;
    private Command command;

    public Gui(){
        this.frame = new JFrame("Santorini");
        this.infoUser = new JLabel();

        register();
        //run();



    }
    public void setUpFrame(){
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null); //this after size and pack
        frame.setVisible(true);
    }

    /*public void run(){
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
        //TODO: qua mettere in infoUser label printWriter.println(userData);

        controllerClient.notifyControllerAction();
    }*/


    public void register(){
        ImageIcon icon = new ImageIcon("ing-sw-2020-Mancuso-Marta-Motta\\src\\main\\resources\\Background.png");
        JLabel introLabel = new JLabel("START THE GAME");
        panel.add(introLabel);
        //TODO cercare label che sparisce dopo x secondi da sola
        //TODO textField for username
    }

    //TODO Username already exist error print, faccio una label

    public static void main(String[] args) {
        new Gui();
    }
}
