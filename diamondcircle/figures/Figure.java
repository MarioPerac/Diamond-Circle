package diamondcircle.figures;

import diamondcircle.game.Field;
import diamondcircle.game.Game;
import diamondcircle.time.ExecutionTime;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.logging.Level;

public abstract class Figure{
    protected int x;
    protected int y;

    protected int numDiamonds;
    protected Color figureColor;
    protected String figureColorName;
    protected ImageIcon figureImage;
    protected boolean fellInGap;
    protected boolean finishedGame;
    protected int currentFieldIndex;
    private final int numPixelsToAdjustHeight = 10;
    protected ArrayList<Field> crossedFields;
    protected ExecutionTime executionTime;
    protected String type;


    protected Figure(Color figureColor)
    {
        this.figureColor = figureColor;
        setFigureColorName();

        x = Game.MATRIX[Game.START_ROW][Game.START_COLUMN].getX();
        y = Game.MATRIX[Game.START_ROW][Game.START_COLUMN].getY() - numPixelsToAdjustHeight;

        crossedFields = new ArrayList<>();
        crossedFields.add(Game.PATH.get(0));
        fellInGap = finishedGame = false;
        executionTime = new ExecutionTime();
        currentFieldIndex = 0;
    }

    public boolean move(int fieldsNum)
    {
        int nextFieldIndex = currentFieldIndex + fieldsNum;

        if(nextFieldIndex < Game.PATH.size())
        {
           crossedFields.addAll(Game.PATH.subList(currentFieldIndex + 1, nextFieldIndex + 1));

           currentFieldIndex = nextFieldIndex;

            Field field = Game.PATH.get(nextFieldIndex);

            if(nextFieldIndex == Game.PATH.size() - 1)
            {
               finishedGame = true;
            }
            else if( field.getFigure() != null)
            {
                return move(1);
            }
            x = field.getX();
            y = field.getY() - numPixelsToAdjustHeight;
            field.setFigure(this);

            return true;
        }

        return false;
    }

    private String crossedPathString()
    {
        String path = "crossed path ";
        for(Field field : crossedFields)
        {
            path += "-" + field.getPosition();
        }

        return path;
    }

    @Override
    public String toString()
    {
        return "("+ type + ", " + figureColorName +"), "  + crossedPathString() + ", time on map " + Long.toString(executionTime.getExecution()) + "[s]" + ",  reached the finish field: " + (fellInGap ?  "NO!" : "YES!");

    }
    public void pickUpDiamonds(Field field)
    {
        numDiamonds += field.getDiamonds();
        field.setDiamonds(0);
    }
    public Color getFigureColor()
    {
        return figureColor;
    }

    public Image getFigureImage()
    {
        return figureImage.getImage();
    }

    public boolean isFellInGap()
    {
        return fellInGap;
    }

    public boolean isFinishedGame() {
        return finishedGame;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public ExecutionTime getExecutionTime()
    {
        return executionTime;
    }

    public ArrayList<Field> getCrossedFields() {
        return crossedFields;
    }

    public int getNumDiamonds() {
        return numDiamonds;
    }

    public String getType()
    {
        return type;
    }

    public int getCurrentFieldIndex() {
        return currentFieldIndex;
    }

    public String getFigureColorName()
    {
        return figureColorName;
    }

    public void setX(int x)
    {
        this.x = x;
    }


    public void setY(int y)
    {
        this.y = y - numPixelsToAdjustHeight;
    }

    public void setFinishedGame(boolean finishedGame) {
        this.finishedGame = finishedGame;
    }

    public void setFellInGap(boolean fellInGap) {
        this.fellInGap = fellInGap;
    }

    public void setNumDiamonds(int numDiamonds)
    {
        this.numDiamonds = numDiamonds;
    }
    public void setFigureColor(Color figureColor)
    {
        this.figureColor = figureColor;
    }

    private void setFigureColorName()
    {
        if(figureColor.equals(Color.RED))
        {
            figureColorName = "RED";
        }
        else if(figureColor.equals(Color.GREEN))
        {
            figureColorName = "GREEN";
        }
        else if(figureColor.equals(Color.BLUE))
        {
            figureColorName = "BLUE";
        }
        else if(figureColor.equals(Color.YELLOW))
        {
            figureColorName = "YELLOW";
        }
    }
}
