package diamondcircle.player;

import java.awt.Color;
import diamondcircle.card.DeckOfCards;
import diamondcircle.card.OrdinaryCard;
import diamondcircle.card.SpecialCard;
import diamondcircle.figures.Figure;
import diamondcircle.figures.RandomFigure;
import diamondcircle.figures.SuperFastFigure;
import diamondcircle.game.Game;
import diamondcircle.game.Gap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.logging.Level;

public class Player extends Thread{

    public Figure figureInMotion;
    private String name;
    private Color playerColor;
    private LinkedList<Figure> figures;
    private ArrayList<Figure> figuresInGap;
    private boolean finishedGame;

    public Player(String name, Color playerColor) {

        this.name = name;
        this.playerColor = playerColor;

        figures = new LinkedList<Figure>();
        figuresInGap = new ArrayList<Figure>();

        for(int i=0; i < 4; i++)
        {
            figures.add(RandomFigure.getRandomFigure(playerColor));
        }

        figureInMotion = figures.getFirst();
        finishedGame = false;
    }

    @Override
    public String toString()
    {
        String playerString = name + ":\n";
        int i=1;
        for(Figure figure : figures)
        {
            playerString += "Figure " + i + " " + figure.toString() + "\n";
            i++;
        }

        for(Figure figure : figuresInGap)
        {
            playerString += "Figure " + i + " " + figure.toString() + "\n";
            i++;
        }

        return  playerString;
    }

    public String getPlayerName()
    {
        return name;
    }
    public void setPlayerName(String name){
        this.name = name;
    }

    public LinkedList<Figure> getFigures() {
        return figures;
    }
    public Color getPlayerColor()
    {return playerColor;}

    public ArrayList<Figure> getFiguresInGap(){return  figuresInGap;}

    public boolean isFinishedGame() {
        return finishedGame;
    }

    public void setFinishedGame(boolean finishedGame) {
        this.finishedGame = finishedGame;
    }

    public void run()
    {
        while(!finishedGame)
        {
            synchronized (this)
            {
                try{
                    wait();
                }catch (InterruptedException e)
                {
                    Game.logger.log(Level.WARNING, e.toString());
                }
			}

            if(figureInMotion.isFinishedGame())
            {
                figureInMotion.getExecutionTime().setStopExecution(true);

                if (figureInMotion.isFellInGap()) {
                    figuresInGap.add(figureInMotion);
                    figures.remove(figureInMotion);
                }else
                {
                    figures.remove(figureInMotion);
                    figures.addLast(figureInMotion);
                }

                if(figures.size() > 0) {
                    figureInMotion = figures.getFirst();

                    if (figureInMotion.isFinishedGame()) {
                        finishedGame = true;
                    }
                    else
                    {
                        figureInMotion.getExecutionTime().start();
                    }
                }
                else
                {
                    finishedGame = true;
                }

            }


            if(!finishedGame) {

                if(!figureInMotion.getExecutionTime().isAlive())
                {
                    figureInMotion.getExecutionTime().start();
                }

                if (DeckOfCards.PULLED_CARD instanceof OrdinaryCard) {

                    if(figureInMotion instanceof SuperFastFigure) {
                        boolean move = figureInMotion.move((2*DeckOfCards.PULLED_CARD.getValue() )+ figureInMotion.getNumDiamonds());
                        Game.moveDescription = name + ": pulled card[" + DeckOfCards.PULLED_CARD.toString() + "], " +
                                "figure[" + figureInMotion.getType()+ ", diamonds=" +figureInMotion.getNumDiamonds() +"], " +
                        ( move ? "crossed[": "can't cross [") + (2*(DeckOfCards.PULLED_CARD.getValue()) + figureInMotion.getNumDiamonds()) + "] fields.";
                    }
                    else
                    {
                        boolean move = figureInMotion.move(DeckOfCards.PULLED_CARD.getValue() + figureInMotion.getNumDiamonds());
                        Game.moveDescription = name + ": pulled card[" + DeckOfCards.PULLED_CARD.toString() + "], " +
                                "figure[" + figureInMotion.getType() + ", diamonds=" +figureInMotion.getNumDiamonds() +"], " +
                        ( move ? "crossed[": "can't cross[") + (DeckOfCards.PULLED_CARD.getValue() + figureInMotion.getNumDiamonds()) + "] fields.";
                    }

                    figureInMotion.setNumDiamonds(0);
                    figureInMotion.pickUpDiamonds(Game.PATH.get(figureInMotion.getCurrentFieldIndex()));

                } else if (DeckOfCards.PULLED_CARD instanceof SpecialCard) {
                    Game.moveDescription = name + ": pulled card [" + DeckOfCards.PULLED_CARD.toString() + "]";
                SpecialCard specialCard = (SpecialCard) DeckOfCards.PULLED_CARD;
                Gap.setGapsOnPath(specialCard.getValue());
                }
            }

            synchronized (Game.currentGame) {		
                Game.currentGame.notify();
            }
        }
    }

}
