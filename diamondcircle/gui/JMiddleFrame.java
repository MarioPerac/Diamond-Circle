package diamondcircle.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import diamondcircle.game.Game;
import java.io.File;

public class JMiddleFrame extends JFrame{

    public JMiddleFrame()
    {
      initComponents();
    }

    public void initComponents(){
        setTitle("DiamondCircle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,700);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(Game.gameIcon.getImage());

        ImageIcon backgroundImage = new ImageIcon("diamondcircle"+File.separator+"game"+File.separator+"MiddleBackground.jpg");
        JLabel background = new JLabel(backgroundImage);
        background.setBounds(0,0,500,700);

        JPanel panel = new JPanel();
        panel.setSize(500,700);
        panel.setLayout(new BorderLayout());
        panel.add(background);

        int x = 200;
        int y = 120;
        int width = 200;
        int height = 40;

        JTextField textFields[] = new JTextField[Game.NUM_PLAYERS];

        for(int i=0; i < Game.NUM_PLAYERS ; i++)
        {
            JLabel text = new JLabel();
            text.setText("Player " + (i+1) + ":");
            text.setBounds(x - 120, y, width, height);
            text.setForeground(new Color(225,228,114));
            text.setFont(new Font("Comic Sans", Font.BOLD, 22));
            

            JTextField textField = new JTextField();
            textField.setBounds(x, y, width, height);
            textField.setText("Player " + (i+1));
            textField.setForeground(new Color(19,33,60));
            textField.setFont(new Font("Comic Sans", Font.PLAIN, 20));
            this.add(text);
            this.add(textField);
            y += 100;
            textFields[i] = textField;
        }

        JButton playButton = new JButton();
        playButton.setBounds(150,550,200,70);
        playButton.setText("PLAY");

        playButton.addActionListener(event -> {

            boolean closeFrame = true;

            for(int i=0; i < Game.NUM_PLAYERS; i++)
            {
                String playerName = textFields[i].getText();
                if(Game.checkPlayerUniqueness(Game.players.get(i).getName(), playerName))
                {
                    if(!playerName.equals(Game.players.get(i).getName()))
                    {
                        Game.players.get(i).setName(playerName);
                    }

                    textFields[i].setBackground(Color.GREEN);
                }
                else
                {
                    textFields[i].setBackground(Color.RED);
                    closeFrame = false;
                }
            }

            if(closeFrame) {
                Game.mainFrame = new JMainFrame();
                Game.mainFrame.setVisible(true);
                dispose();
            }

        });

        this.add(playButton);
        this.add(panel);
    }

}
