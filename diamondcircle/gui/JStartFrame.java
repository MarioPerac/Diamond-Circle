package diamondcircle.gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import diamondcircle.game.Game;
import java.io.File;

public class JStartFrame extends JFrame{

    public JStartFrame(){
        initComponents();
    }

    public void initComponents()
    {
        setTitle("DiamondCircle");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,700);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(Game.gameIcon.getImage());

        ImageIcon titleImage = new ImageIcon("diamondcircle"+File.separator+"game"+File.separator+"StartBackground.jpg");
        JLabel background = new JLabel(titleImage);
        background.setBounds(0,0, 515 ,715);

        JButton startButton = new JButton();
        startButton.setBounds(160,460,150,75);
        startButton.setText("START");
        startButton.setFocusable(false);
        startButton.setForeground(new Color(19,33,60));
        startButton.setFont(new Font("Comic Sans", Font.BOLD, 25));

        startButton.addActionListener(event -> {
            Game.middleFrame = new JMiddleFrame();
            dispose();
            Game.middleFrame.setVisible(true);
        });

        background.add(startButton);
        this.add(background);

    }
}
