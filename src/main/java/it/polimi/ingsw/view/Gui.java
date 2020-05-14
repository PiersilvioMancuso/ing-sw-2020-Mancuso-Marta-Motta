package it.polimi.ingsw.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui implements ActionListener {

    private JFrame frame;
    private ImageIcon iconFrame;
    private JPanel panel;
    private JButton button;
    private JLabel label;

    public Gui(){

        frame = new JFrame();

        panel = new JPanel();
        button= new JButton("Submit");
        label = new JLabel("Press enter");

        button.addActionListener(this);

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);

        iconFrame = new ImageIcon("src\\main\\resources\\Background #19028.png");
        frame.setIconImage(iconFrame);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Santorini");
        frame.pack();
        frame.setVisible(true);


    }

    private void setIconImage(ImageIcon iconFrame) {
    }


    public static void main(String[] args) {
        new Gui();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
