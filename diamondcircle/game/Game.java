package diamondcircle.game;

import diamondcircle.card.DeckOfCards;
import diamondcircle.gui.JMainFrame;
import diamondcircle.gui.JMiddleFrame;
import diamondcircle.gui.JStartFrame;
import diamondcircle.player.Player;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import diamondcircle.figures.GhostFigure;
import diamondcircle.time.ExecutionTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

public class Game extends Thread{
    public static Field[][] MATRIX;
    public static ArrayList<Field> PATH;
    public static LinkedList<Player> players;
    public static DeckOfCards deckOfCards;
    public static int MATRIX_DIMENSION;
    public static int NUM_PLAYERS;
    public static int START_ROW;
    public static int START_COLUMN;
    public static int FINAL_POSITION;
    public static boolean GAME_OVER;
    public static boolean PAUSE;
    public static JStartFrame startFrame;
    public static JMiddleFrame middleFrame;
    public static JMainFrame mainFrame;
    public static Thread mainFrameThread;
    public static ImageIcon diamond;
    public static ImageIcon gap;
    public static ImageIcon gameIcon;
    public static ExecutionTime executionTime;
    public static String moveDescription;
    public static String resultFileName;
    public static int gamesPlayedNum;
    public static File[] resultFiles;
	public static Game currentGame;

    public static Logger logger = Logger.getLogger(Game.class.getName());

    static{
        try{
            logger.setUseParentHandlers(false);
            logger.addHandler(new FileHandler("diamondcircle"+File.separator+"logfolder"+File.separator+"game.log"));
        }catch(Exception e)
        {
            throw new RuntimeException();
        }
    }

    public static Color getPlayerColor(int position)
    {
        if(position == 0)
        {
            return  Color.RED;
        }
        else if(position == 1)
        {
            return Color.BLUE;
        }
        else if(position == 2)
        {
            return Color.GREEN;
        }
        else if(position == 3)
        {
            return Color.YELLOW;
        }
        return null;
    }

    public static void generateGamePath()
    {
        PATH = new ArrayList<>();

        int borderStartRow = 1;
        int borderStartColumn = 0;
        int borderEnd = MATRIX_DIMENSION - 1;

        int currentRow = START_ROW;
        int currentColumn = START_COLUMN;

        boolean incrementModeRow = true;
        boolean incrementModeColumn = true;

        while(currentRow != FINAL_POSITION || currentColumn != FINAL_POSITION)
        {
            PATH.add(MATRIX[currentRow][currentColumn]);

            if(incrementModeRow)
            {
                if(currentRow != borderEnd)
                {
                    currentRow++;
                }
                else
                {
                    incrementModeRow = false;
                    currentRow--;
                }
            }
            else
            {
                if(currentRow != borderStartRow)
                {
                    currentRow--;
                }
                else
                {
                    incrementModeRow = true;

                    borderStartRow++;
                    borderEnd--;
                }
            }

            if(incrementModeColumn)
            {
                if(currentColumn != borderEnd)
                {
                    currentColumn++;
                }
                else
                {
                    incrementModeColumn = false;
                    currentColumn--;
                }
            }
            else
            {
                if(currentColumn != borderStartColumn)
                {
                    currentColumn--;
                }
                else
                {
                    incrementModeColumn = true;
                    currentColumn++;

                    borderStartColumn++;
                }
            }
        }

        PATH.add(MATRIX[currentRow][currentColumn]);
    }

    public static boolean checkPlayerUniqueness(String previousName, String newName) {

        if(newName.isEmpty())
        {
            return false;
        }

        for (Player player : players)
        {
            String playerName = player.getName();

                if(!previousName.equals(playerName) && newName.equals(playerName))
                {
                    return false;
                }
        }

        return true;

    }

    public static void colorTheGamePath()
    {
        PATH.stream().forEach(field -> field.setFieldColor(new Color(19,33,60)));
    }

    public static boolean allPlayersFinished()
    {
        for(Player player : players) {
            if (!player.isFinishedGame()) {
                return false;
            }
        }
        return  true;
    }
	
	@Override
    public void run()
    {
		startFrame = new JStartFrame();
        startFrame.setVisible(true);
		
        Player player = Game.players.getFirst();
        Game.executionTime.start();

        while(!Game.GAME_OVER) {

            if(Game.PAUSE)
            {  
		
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Game.logger.log(Level.WARNING, e.toString());
                    }
                }
            }

                if(!player.isFinishedGame())
                {

                synchronized (this) {
					
				synchronized (player) {
                    player.notify();
                }
					
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Game.logger.log(Level.WARNING, e.toString());
                    }
                }
		
                JMainFrame.descriptionLabel.setText(Game.moveDescription);
                JMainFrame.timeLabel.setText("Game duration: " + (Game.executionTime.getEndInSeconds() - Game.executionTime.getStartInSeconds()) + "[s]");
                mainFrame.repaint();

				 synchronized (Game.deckOfCards) {
                        Game.deckOfCards.notify();
                }
				
				  try {
                Thread.sleep(1000);
				}catch(InterruptedException e)
				{
                Game.logger.log(Level.WARNING, e.toString());
				}
				
                Gap.eatFiguresOnGap();
                }

                if(!Game.allPlayersFinished())
                {
                    do {
                        Game.players.removeFirst();
                        Game.players.addLast(player);

                        player = Game.players.getFirst();
                    }while(player.isFinishedGame());
                }
                else
                {
                    Game.executionTime.setStopExecution(true);
                    Game.GAME_OVER = true;

                    try (PrintWriter pw = new PrintWriter(new BufferedWriter( new FileWriter(Game.resultFileName))))
					{
                       
                        for(Player gamePlayer : Game.players)
                        {
                            pw.println(gamePlayer.toString());
                        }
                        pw.println("Total game duration: " + Long.toString(Game.executionTime.getExecution()) + "!");
                    
                    }catch (IOException ioException)
                    {
                        Game.logger.log(Level.WARNING, ioException.toString());
                    }
                }
        }
    }
	
    public static void main(String[] args) {
		
     GAME_OVER = false;
	 
	 if(args.length < 4)
	 {
		 throw new RuntimeException("Missing parameters.");
	 }
		
	 for(int i=0; i < args.length -1; i++)
       {
           if("-dimension".equals(args[i]))
           {
               i += 1;
               MATRIX_DIMENSION = Integer.parseInt(args[i]);

               if(MATRIX_DIMENSION < 7 || MATRIX_DIMENSION > 10)
               {
                   throw new RuntimeException("Unavailable dimension: " + MATRIX_DIMENSION);
               }
           }
           else if("-numPlayers".equals(args[i]))
           {
               i += 1;
               NUM_PLAYERS = Integer.parseInt(args[i]);

               if(NUM_PLAYERS < 2 || NUM_PLAYERS > 4)
               {
                   throw new RuntimeException("Unavailable number of players: " + NUM_PLAYERS);
               }
           }
		   else
		   {
			   throw new RuntimeException("Unknown parameters.");
		   }
       }
     
        START_ROW = 0;
        START_COLUMN = MATRIX_DIMENSION / 2;
        FINAL_POSITION = MATRIX_DIMENSION / 2;

        if (MATRIX_DIMENSION % 2 == 0) {
            START_COLUMN--;
            FINAL_POSITION--;
        }

        diamond = new ImageIcon("diamondcircle"+File.separator+"game"+File.separator+"Diamond.png");
        gameIcon = new ImageIcon("diamondcircle"+File.separator+"game"+File.separator+"DiamondIcon.png");
        gap = new ImageIcon("diamondcircle"+File.separator+"game"+File.separator+"Gap.png");

        executionTime = new ExecutionTime();
        PAUSE = true;

        deckOfCards = new DeckOfCards();
        deckOfCards.start();

        MATRIX = new Field[MATRIX_DIMENSION][MATRIX_DIMENSION];

        for (int i = 0; i < MATRIX_DIMENSION; i++) {
            for (int j = 0; j < MATRIX_DIMENSION; j++) {
                MATRIX[i][j] = new Field();
                if (Game.MATRIX_DIMENSION == 7 || Game.MATRIX_DIMENSION == 8) {
                    int x = j * 60 + 290;
                    int y = i * 50 + 50;
                    MATRIX[i][j].setX(x);
                    MATRIX[i][j].setY(y);
                } else {
                    int x = j * 60 + 220;
                    int y = i * 50+ 20;
                    MATRIX[i][j].setX(x);
                    MATRIX[i][j].setY(y);
                }

                MATRIX[i][j].setPosition(i*MATRIX_DIMENSION + j + 1);
            }
        }
        generateGamePath();
        colorTheGamePath();

        players = new LinkedList<Player>();

        for (int i = 0; i < NUM_PLAYERS; i++) {
            players.addLast(new Player("Player " + (i + 1), getPlayerColor(i)));
        }

        GhostFigure ghostFigure = new GhostFigure();
        ghostFigure.start();

        for(Player player : players)
        {
            player.start();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");

        Date date = new Date();
        resultFileName  =  "diamondcircle"+File.separator+"results"+File.separator+"GAME_" + formatter.format(date) +".txt";

        File filePath = new File("diamondcircle"+File.separator+"results");
        resultFiles = filePath.listFiles();
        Game.gamesPlayedNum = resultFiles.length;
		
		currentGame = new Game();
		currentGame.start();

    }
}
