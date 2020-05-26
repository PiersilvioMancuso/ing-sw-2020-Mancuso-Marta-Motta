package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ControllerClient;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ModelColor;
import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.god.God;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Gui extends View {

    public static void main(String[] args) {
        new Gui();
    }

    protected java.util.List<Cell> availableCell;
    protected java.util.List<God> availableGod;
    protected List<ModelColor> availableColor;

    protected ImageIcon icon;
    protected ImageIcon backgroundImage;
    protected JPanel panel;
    protected JButton submit;
    protected JLabel background;
    protected JLabel userInfo;
    protected JTextField userInfoText;
    protected static String userData;
    protected Command command;
    protected ControllerClient controllerClient;

    public Gui(){

        setUpFrame();

    }

    public void setUpFrame(){
        JFrame frame = new JFrame();
        frame.add(panel, BorderLayout.CENTER);
        frame.setTitle("SANTORINI");
        ImageIcon iconFrame = new ImageIcon("src\\main\\resources\\santoriniicon.png");
        frame.setIconImage(iconFrame.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800,730);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); //this after size and pack
        frame.setVisible(true);

    }



    public void run(){
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
    }


    @Override
    public void printError(String message) {//TODO: Check message Length
        JDialog error = new JDialog();
        error.setTitle("ERROR");
        JTextArea errorPanel = new JTextArea(message);
        errorPanel.setColumns(40);

        errorPanel.setLayout(new FlowLayout());
        error.setLayout(new FlowLayout());
        error.add(errorPanel);
        error.pack();
        error.setSize(500,100);
        error.setLocationRelativeTo(null);
        error.setVisible(true);
    }

    public ControllerClient getControllerClient() {
        return controllerClient;
    }


    public void register() {

        userInfo = new JLabel("INSERT USERNAME");
        userInfoText = new JTextField(20);

        JLabel userAge = new JLabel("INSERT AGE");
        final JTextField userAgeText = new JTextField(3);

        JLabel userIp = new JLabel("INSERT IP ADDRESS");
        final JTextField userIpText = new JTextField(16);

        userInfo.setBounds(10,20,200,25);
        userInfoText.setBounds(220 ,20, 165,25);
        submit.setBounds(10,50,80,25);

        submit = new JButton("SUBMIT");
        icon = new ImageIcon("src\\main\\resources\\labelbutton_modeselect.png");
        submit.setIcon(icon);
        this.userData = null;
        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                userData = "username=" + userInfoText.getText() + ";";
                userData += "address=" + userIpText.getText() + ";";
                userData += "age=" + userAgeText.getText() + ";";

                System.out.println(userData);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                submit.setToolTipText("Click here to submit your data");
            }


        });


        backgroundImage = new ImageIcon("src\\main\\resources\\SantoriniIntro.png"); //TODO vedere se biene printata guista o devo fare il resize della foto
        background.setIcon(backgroundImage);
        background.setSize(800,730); //TODO vedere se entra nel frame o lo taglia

        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout(new FlowLayout()); //new GridLayout(0,1)); zero righe una colonna
        panel.setAlignmentX(0);

        panel.add(background);
        panel.add(userInfo);
        panel.add(userInfoText);
        panel.add(userAge);
        panel.add(userAgeText);
        panel.add(userIp);
        panel.add(userIpText);
        panel.add(submit);

    }

    /**Print the error that the name already exist
     *
     */
    public void usernameExist() {
        //TODO settare posizione perchè tengo il panel del register
        JLabel usernameExist = new JLabel("Username already exist please change it");
        panel.add(usernameExist);
    }

    public void color() {
        this.userData = null;
        userInfo = new JLabel("CHOOSE ONE OF THE FOLLOWING COLOR:");
        icon = new ImageIcon("src\\main\\resources\\icon_player.png");
        for(ModelColor color : availableColor ){
            //TODO fare un gruppo di button??
            submit = new JButton(availableColor.toString());
            submit.setBackground(Color.decode(availableColor.toString()));
            submit.setIcon(icon);


            submit.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    userData = "color=" + availableColor.toString();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    submit.setToolTipText("Click here to choose" + availableColor);
                }
            });

            //TODO mettere sti cazzo di bottoni tondi buttonColor.setBorder(new RoundedBorder(10));
            //TODO add to panel but in what way?
            //TODO scegliere un layout
            //printWriter.println(print + availableColor.indexOf(color) + " - " + color + CliColor.RESET);

        }
    }

    public void godListThree() {
        userInfo = new JLabel("CHOOSE THREE GOD:");
        ButtonGroup group = new ButtonGroup();
        for(God god : availableGod ){
            submit = new JButton(availableGod.toString());
            submit.setBorder(null);
            final String path = "src\\main\\resources\\god\\" + availableGod.toString() + ".png";
            backgroundImage = new ImageIcon(path);
            submit.setIcon(backgroundImage);
            submit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    icon = new ImageIcon("src\\main\\resources\\god\\frame_blue.png");
                    //TODO come cazzo si setta il doppio icon
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                }
            });
            group.add(submit);
        }
        this.userData="";
        //TODO mouseListener for group button
        for(int i=0; i<3; i++){
            /*printWriter.println(CliColor.CYAN + "\n" + "! Choose a god from the previous list:" + CliColor.RESET);
            scan = scanner.nextLine();
            userData += "god=" + scan + ";";*/
        }
    }

    public void godListTwo() {
        userInfo = new JLabel("CHOOSE TWO GOD:");
        ButtonGroup group = new ButtonGroup();
        for(God god : availableGod ){
            submit = new JButton(availableGod.toString());
            String path = "src\\main\\resources\\god\\" + availableGod.toString() + ".png";
            icon = new ImageIcon(path);
            group.add(submit);
        }
        this.userData="";
        //TODO mouseListener for group button
    }

    public void god() {
    }

    public void setWorkerPosition() {
    }

    public void moveWorker() {
    }

    public void usePower() {
        JLabel power = new JLabel("Which worker?");
        //listener per vedere quale pigia e se è il suo
        //userData= "build=" + bottone.posizione + ";";
        JButton powerOn = new JButton("USE POWER");
        JButton powerOff = new JButton("NOT USE POWER");
        ImageIcon powerOnImage = new ImageIcon("src\\main\\resources\\powerOn.png");
        ImageIcon powerOffImage = new ImageIcon("src\\main\\resources\\powerOff.png");
        powerOn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                userData = "power";
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }
        });
        ButtonGroup powerGroup = new ButtonGroup();
        powerGroup.add(powerOn);
        powerGroup.add(powerOff);
    }

    public void build() {

    }

    public void loose() {
        userInfo = new JLabel();
        userInfo.setText("Sorry, you lose");
        userInfo.setHorizontalAlignment(SwingConstants.CENTER);
        userInfo.setVerticalAlignment(SwingConstants.CENTER);
        backgroundImage = new ImageIcon("src\\main\\resources\\bg_win_loose\\bg_modeselect_loose.png"); //TODO vedere se biene printata guista o devo fare il resize della foto
        background.setIcon(backgroundImage);
        background.setSize(800,730); //TODO vedere se entra nel frame o lo taglia
        //background.setOpaque(true); TODO controllare se mi serve o basta mettere prima back e poi userInfo
        panel = new JPanel();
        panel.add(background);
        panel.add(userInfo);
    }

    public void win(){
        userInfo = new JLabel();
        userInfo.setText("OH MAN, YOU WIN!");
        userInfo.setHorizontalAlignment(SwingConstants.CENTER);
        userInfo.setVerticalAlignment(SwingConstants.CENTER);
        backgroundImage = new ImageIcon("src\\main\\resources\\bg_win_loose\\bg_SantoriniWin.png"); //TODO vedere se biene printata guista o devo fare il resize della foto
        background.setIcon(backgroundImage);
        background.setSize(800,730); //TODO vedere se entra nel frame o lo taglia
        //background.setOpaque(true); TODO controllare se mi serve o basta mettere prima back e poi userInfo
        panel = new JPanel();
        panel.add(background);
        panel.add(userInfo);
    }

    public void players(){
        userInfo = new JLabel("HOW MANY PLAYERS?");
        userInfoText = new JTextField(2);
        submit = new JButton("SUBMIT");
        icon = new ImageIcon("src\\main\\resources\\labelbutton_modeselect.png");
        submit.setIcon(icon);
        this.userData = null;
        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                userData = "players=" + userInfoText.getText() + ";";

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                submit.setToolTipText("Click here to submit how many players");
            }
        });

        userInfoText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    userData = "players=" + userInfoText.getText() + ";";
                }
            }
        });

        backgroundImage = new ImageIcon("src\\main\\resources\\SantoriniIntro.png"); //TODO vedere se biene printata guista o devo fare il resize della foto
        background.setIcon(backgroundImage);
        background.setSize(800,730); //TODO vedere se entra nel frame o lo taglia

        panel = new JPanel();
        panel.add(background);
        panel.add(userInfo);
        panel.add(userInfoText);
        panel.add(submit);
    }

    public void quit(){
        userInfo = new JLabel();
        userInfo.setText("You're quitting, you have done");
        userInfo.setHorizontalAlignment(SwingConstants.CENTER);
        userInfo.setVerticalAlignment(SwingConstants.TOP);
        backgroundImage = new ImageIcon("src\\main\\resources\\SantoriniExit.png"); //TODO vedere se biene printata guista o devo fare il resize della foto
        background.setIcon(backgroundImage);
        background.setSize(800,730); //TODO vedere se entra nel frame o lo taglia
        //background.setOpaque(true); TODO controllare se mi serve o basta mettere prima back e poi userInfo
        panel = new JPanel();
        panel.add(background);
        panel.add(userInfo);
    }

    public static void printBoard(JPanel panel, ModelGame modelGame, List<Cell> availableCell){

        //if (modelGame == null) return;
        panel = new JPanel();

        panel.setLayout(new GridLayout(5,5));
//        ButtonGroup test = new ButtonGroup();
        int count = 0;

        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                CellButton button = new CellButton(i, j);


                // If is there any worker
                if (modelGame.getWorkerListPosition().contains(new Cell(i, j))){
                    button.setButtonColor(modelGame.getWorkerFromPosition(new Cell(i, j)).getColor());
                }



                if (availableCell.contains(new Cell(i, j))){
                    button.setBorderPainted(true);
                    button.setBorder(new LineBorder(button.getColor()));
                }

                button.setHeight(modelGame.getBoard().getCell(new Cell(i, j)).getHeight());
                System.out.println(0);
                button.addListener();
                System.out.println(count++);
                button.setVisible(true);

                // --- If not group added
                panel.add(button);
                panel.setVisible(true);
            }
        }
        System.out.println(1);
        System.out.println(panel.getComponentCount());
        //printUser();
    }

}
