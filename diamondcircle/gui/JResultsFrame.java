package diamondcircle.gui;

import diamondcircle.game.Game;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Color;
import java.io.File;

public class JResultsFrame extends JFrame {

    public JResultsFrame()
    {
        initComponents();
    }

    private void initComponents()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        for(File file : Game.resultFiles)
        {
            JButton fileName = new JButton();
            fileName.setFocusable(false);
            fileName.setText(file.getName());
            fileName.addActionListener(e -> {
            JFileFrame fileFrame = new JFileFrame("diamondcircle"+File.separator+"results" +File.separator+ file.getName(), file.getName());
            fileFrame.setVisible(true);
            });
            mainPanel.add(fileName);
        }

        setTitle("Result files list");
        setSize(300,700);
        setLocationRelativeTo(null);
        setIconImage(Game.gameIcon.getImage());
        mainPanel.setBackground( new Color(19,33,60));

        this.add(mainPanel);
    }
}
