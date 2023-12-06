package diamondcircle.gui;

import diamondcircle.game.Game;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import diamondcircle.game.Field;
import diamondcircle.figures.Figure;
import diamondcircle.game.Gap;
import diamondcircle.player.Player;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import diamondcircle.card.Card;

public class JMainFrame extends JFrame{

    private JPanel header;
    private JPanel leftPanel;
    private JPanel playersPanel;
    private JPanel rightPanel;
    private JPanel mainPanel;
    private JPanel descriptionPanel;
    private JPanel cardPanel;
    private JPanel resultsPanel;
    private JPanel timePanel;
    public static JLabel descriptionLabel;
    public static JLabel timeLabel;
    private JResultsFrame resultsFrame;
	private JPanel matrixPanel;

    public JMainFrame() {
        initComponents();
    }

    public void initComponents()
    {
        setTitle("DiamondCircle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(new BorderLayout(12,10));
        setIconImage(Game.gameIcon.getImage());

        header = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        mainPanel = new JPanel();
        matrixPanel = new JPanel();
        descriptionPanel = new JPanel();
        resultsFrame = new JResultsFrame();
        cardPanel = new JPanel(){
        @Override
        public void paint(Graphics g)
        {
            g.setFont(new Font("Comic Sans", Font.PLAIN, 20));
            g.setColor(new Color(19,33,60));
            g.drawImage(Game.deckOfCards.PULLED_CARD.getCardImage().getImage(), Card.x, Card.y , null);
        }
        };
        resultsPanel = new JPanel();
        timePanel = new JPanel();
        matrixPanel = new JPanel()
        {
            @Override
            public  void paint(Graphics g)
            {
                ImageIcon mainPanelBackground = new ImageIcon("diamondcircle"+File.separator+"gui"+File.separator+"MainPanelBackground.png");
                g.drawImage(mainPanelBackground.getImage(), 0,0,null);

                    for (int i = 0; i < Game.MATRIX_DIMENSION; i++) {
                        for (int j = 0; j < Game.MATRIX_DIMENSION; j++) {
                            g.setColor(Game.MATRIX[i][j].getFieldColor());

                            if (Game.MATRIX_DIMENSION == 7 || Game.MATRIX_DIMENSION == 8) {
                                int x = j * 60 + 290;
                                int y = i * 50 + 50;
                                g.fill3DRect(x, y, 60, 50, true);
                            } else {
                                int x = j * 60 + 220;
                                int y = i * 50 + 20;
                                g.fill3DRect(x, y, 60, 50, true);
                            }

                            if(Game.PATH.contains(Game.MATRIX[i][j])) {
                                g.setColor(new Color(249, 207, 29));
                                g.setFont(new Font("Comic Sans", Font.PLAIN, 20));
                                g.drawString(Integer.toString(Game.MATRIX[i][j].getPosition()), Game.MATRIX[i][j].getX() + 20, Game.MATRIX[i][j].getY() + 30);
                            }
                            if(Game.MATRIX[i][j].getDiamonds() > 0)
                            {
                                g.drawImage(Game.diamond.getImage(), Game.MATRIX[i][j].getX() + 10, Game.MATRIX[i][j].getY() + 10, null);
                            }
                            if(Game.MATRIX[i][j].isThereGap())
                            {
                                g.drawImage(Game.gap.getImage(), Game.MATRIX[i][j].getX(), Game.MATRIX[i][j].getY(), null);

                            }
                        }
                    }

                    ImageIcon image = new ImageIcon("diamondcircle"+File.separator+"game"+File.separator+"START.png");
                    g.drawImage(image.getImage(), Game.MATRIX[Game.START_ROW][Game.START_COLUMN].getX(),Game.MATRIX[Game.START_ROW][Game.START_COLUMN].getY() , null);

                ImageIcon image2 = new ImageIcon("diamondcircle"+File.separator+"game"+File.separator+"FINISH.png");
                g.drawImage(image2.getImage(), Game.MATRIX[Game.FINAL_POSITION][Game.FINAL_POSITION].getX(),Game.MATRIX[Game.FINAL_POSITION][Game.FINAL_POSITION].getY() , null);

                for(Player player : Game.players)
                {
                   LinkedList<Figure> playerFigures = player.getFigures();
                    for(Figure figure : playerFigures)
                    {
                       Image figureImage = figure.getFigureImage();
                       g.drawImage(figureImage, figure.getX(), figure.getY(), null);
                    }
                }
                }
            };

       descriptionPanel.setBorder(BorderFactory.createMatteBorder(4,4,4,4, new Color(19,33,60)));
        cardPanel.setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.black));
		header.setBorder(BorderFactory.createMatteBorder(4,4,4,4,new Color(19,33,60)));
        leftPanel.setBorder(BorderFactory.createMatteBorder(4,4,4,4,new Color(19,33,60)));
        rightPanel.setBorder(BorderFactory.createMatteBorder(4,4,4,4,new Color(19,33,60)));

        header.setBackground(new Color(158,198,79));
        mainPanel.setBackground(new Color(19,33,60));
        descriptionPanel.setBackground(new Color(215,249,147));
        leftPanel.setBackground( new Color(215,249,147));
        rightPanel.setBackground( new Color(215,249,147));
        resultsPanel.setBackground(new Color(215,249,147));
        timePanel.setBackground(new Color(19,33,60));

        header.setPreferredSize(new Dimension(100,150));
        mainPanel.setPreferredSize(new Dimension(750,650));
        descriptionPanel.setPreferredSize(new Dimension(100,100));
        leftPanel.setPreferredSize(new Dimension(250,100));
        rightPanel.setPreferredSize(new Dimension(250,100));
        cardPanel.setPreferredSize(new Dimension(250, 450));
        resultsPanel.setPreferredSize(new Dimension(250, 100));
        timePanel.setPreferredSize(new Dimension(250, 25));
        matrixPanel.setPreferredSize(new Dimension(600,500));

        JLabel currentCardText = new JLabel();
        currentCardText.setText("Current Card");
        currentCardText.setForeground(new Color(249,207,29));
        currentCardText.setFont(new Font("Comic Sans", Font.BOLD, 23));
        currentCardText.setBounds(55,30,148,35);
        currentCardText.setBorder(BorderFactory.createLineBorder(new Color(249,207,29),2));
        currentCardText.setBackground(new Color(19,33,60));
        currentCardText.setOpaque(true);
        rightPanel.add(currentCardText);

        JButton startStopButton = new JButton();
        startStopButton.setText("RUN");
        startStopButton.setFocusable(false);
        startStopButton.setForeground(new Color(19,33,60));
        startStopButton.setFont(new Font("Comic Sans", Font.BOLD, 25));
        startStopButton.setBackground(new Color(215,249,147));
        startStopButton.setPreferredSize(new Dimension(150,50));
        startStopButton.addActionListener(event -> {

            if(Game.GAME_OVER)
            {
                startStopButton.setEnabled(false);
            }

           Game.PAUSE = !Game.PAUSE;

           if(Game.PAUSE)
           {
               startStopButton.setText("RUN");
               startStopButton.setBackground(new Color(215,249,147));
           }
           else
           {   
               synchronized (Game.currentGame)
               {
                   Game.currentGame.notify();
               }
			   	   
               startStopButton.setText("STOP");
               startStopButton.setBackground(new Color(225,141,141));
           }
        });

        leftPanel.setLayout(new GridLayout(Game.NUM_PLAYERS*4 + Game.NUM_PLAYERS, 1));
        for(Player player : Game.players)
        {
            JLabel playerName = new JLabel();
            playerName.setText(player.getName() + ":");
            playerName.setForeground(player.getPlayerColor());
            playerName.setBackground(new Color(158,198,79));
            playerName.setBounds(0,0,120,30);
            playerName.setOpaque(true);
            playerName.setFont(new Font("Comic Sans", Font.BOLD, 25));
            leftPanel.add(playerName);

            for(Figure figure : player.getFigures())
            {
                JLabel figureName = new JLabel();
                figureName.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JFrame newFrame = new JFrame()
                        {
                            @Override
                            public void paint(Graphics g) {

                                ArrayList<Field> figureCrossedFields = figure.getCrossedFields();

                                for (int i = 0; i < Game.MATRIX_DIMENSION; i++) {
                                    for (int j = 0; j < Game.MATRIX_DIMENSION; j++) {

                                        int x = j * 60;
                                        int y = i * 50;

                                        if(figureCrossedFields.contains(Game.MATRIX[i][j]))
                                        {
                                            g.setColor(figure.getFigureColor());
                                        }
                                        else
                                        {
                                            g.setColor(Game.MATRIX[i][j].getFieldColor());
                                        }
                                        g.fill3DRect(x, y, 60, 50, true);

                                        if(Game.PATH.contains(Game.MATRIX[i][j])) {
                                            g.setColor(new Color(249, 207, 29));
                                            g.setFont(new Font("Comic Sans", Font.PLAIN, 20));
                                            g.drawString(Integer.toString(Game.MATRIX[i][j].getPosition()), x +20, y + 30);
                                        }
                                    }
                                }
                            }
                        };

                        newFrame.setTitle("Crossed fields " + figure.getType());
                        newFrame.setLocationRelativeTo(null);
                        newFrame.setSize(new Dimension(Game.MATRIX_DIMENSION*60, Game.MATRIX_DIMENSION*50));
                        newFrame.setVisible(true);
                        newFrame.setResizable(false);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        figureName.setForeground(figure.getFigureColor());
                        figureName.setFocusable(true);
                    }

                    @Override
                    public void mouseExited(MouseEvent e)
                    {
                        figureName.setForeground(new Color(19,33,60));
                    }
                });
                figureName.setForeground(new Color(19,33,60));
                figureName.setFont(new Font("Comic Sans", Font.BOLD, 18));
                figureName.setText("-" + figure.getType());

                leftPanel.add(figureName);

            }
        }

        descriptionLabel = new JLabel();
        descriptionLabel.setForeground(new Color(19,33,60));
        descriptionLabel.setFont(new Font("Comic Sans", Font.PLAIN, 20));

        timeLabel = new JLabel();
        timeLabel.setForeground(new Color(249, 207, 29));
        timeLabel.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        timeLabel.setLocation(15,20);
        timePanel.add(timeLabel);

        JButton showResults = new JButton();
        showResults.setText("Result files list");
        showResults.setFocusable(false);
        showResults.setBackground(new Color(19,33,60));
        showResults.setForeground(new Color(249, 207, 29));
        showResults.setFont(new Font("Comic Sans", Font.BOLD, 22));
        showResults.addActionListener(event->{
            resultsFrame.setVisible(true);
        });
        resultsPanel.add(showResults);
        resultsPanel.setLayout(new GridBagLayout());

        JLabel numberGamesText = new JLabel();
        numberGamesText.setText("Number of games played: " + Game.gamesPlayedNum);
        numberGamesText.setForeground(new Color(249, 207, 29));
        numberGamesText.setBackground(new Color(19,33,60));
        numberGamesText.setOpaque(true);
        numberGamesText.setPreferredSize(new Dimension(265,35));
        numberGamesText.setFont(new Font("Comic Sans", Font.PLAIN, 19));
        numberGamesText.setBorder(BorderFactory.createLineBorder(new Color(249,207,29),2));

        ImageIcon diamodCircle = new ImageIcon("diamondcircle"+File.separator+"game"+File.separator+"DiamondCircle.png");
        JLabel diamondCircleImage = new JLabel();
        diamondCircleImage.setIcon(diamodCircle);

        JPanel headerLeftPanel = new JPanel();
        headerLeftPanel.setOpaque(false);
        headerLeftPanel.setLayout(new GridBagLayout());
        headerLeftPanel.add(numberGamesText);

        JPanel headerCenterPanel = new JPanel();
        headerCenterPanel.setOpaque(false);
        headerCenterPanel.setLayout(new GridBagLayout());
        headerCenterPanel.add(diamondCircleImage);

        JPanel headerRightPanel = new JPanel();
        headerRightPanel.setOpaque(false);
        headerRightPanel.setPreferredSize(new Dimension(220,150));
        headerRightPanel.setLayout(new GridBagLayout());
        headerRightPanel.add(startStopButton);

        header.setLayout(new BorderLayout());
        header.add(headerLeftPanel, BorderLayout.WEST);
        header.add(headerCenterPanel, BorderLayout.CENTER);
        header.add(headerRightPanel, BorderLayout.EAST);


        timePanel.setLayout(new GridBagLayout());

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(descriptionPanel, BorderLayout.SOUTH);
        mainPanel.add(matrixPanel);

        rightPanel.setLayout(new BorderLayout(0,12));
        rightPanel.add(cardPanel, BorderLayout.NORTH);
        rightPanel.add(resultsPanel, BorderLayout.SOUTH);
        rightPanel.add(timePanel, BorderLayout.CENTER);

        descriptionPanel.setLayout(new GridBagLayout());
        descriptionPanel.add(descriptionLabel);

        this.add(header, BorderLayout.NORTH);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
    }


    
}
