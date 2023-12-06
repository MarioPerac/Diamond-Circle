package diamondcircle.gui;

import diamondcircle.game.Game;

import javax.swing.JTextArea;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;


public class JFileFrame extends JFrame {

    private String path;
    private String frameTitle;

    public JFileFrame(String path, String frameTitle)
    {
        this.path = path;
        this.frameTitle = frameTitle;
        initComponents();
    }

    private void initComponents()
    {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            List<String> fileContent = Files.readAllLines(Paths.get(path));
            for(String line : fileContent)
            {
                stringBuffer.append(line + "\n");
            }


        }catch (IOException ioException)
        {
            Game.logger.log(Level.WARNING, ioException.toString());
        }

        JTextArea textArea = new JTextArea();
        textArea.setEnabled(false);
        textArea.setText(stringBuffer.toString());
        textArea.setFont(new Font("Comic Sans", Font.PLAIN, 17));
        textArea.setBackground(new Color(19,33,60));
        this.add(textArea);
        this.setTitle(frameTitle);
        this.setIconImage(Game.gameIcon.getImage());
        this.pack();
    }
}
