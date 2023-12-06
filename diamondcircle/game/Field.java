package diamondcircle.game;

import java.awt.Color;
import diamondcircle.figures.Figure;

public class Field {
    private Figure figure;
    private int x;
    private int y;
    private Color fieldColor = new Color(215,249,147);
    private int diamonds;
    private  boolean gap;
    private int position;

    public Field()
    {
        diamonds = 0;
        gap = false;
    }

    public Field(Figure figure, int x, int y)
    {
        this.figure = figure;
        this.x = x;
        this.y = y;

    }

    @Override
    public boolean equals(Object object)
    {
        Field secondField = (Field)object;
        if(this.getX() == secondField.getX() && this.getY() == secondField.getY())
        {
            return true;
        }

        return false;
    }

    public Figure getFigure()
    {
        return figure;
    }

    public int getX()
    {
        return  x;
    }
    public int getY()
    {
        return  y;
    }

    public void addDiamonds(int diamonds)
    {
        this.diamonds +=diamonds;
    }

    public void setDiamonds(int diamonds)
    {
        this.diamonds = diamonds;
    }

    public int getDiamonds()
    {
       return diamonds;
    }
    public boolean isThereGap()
    {
        return gap;
    }

    public void setFigure(Figure figure)
    {
        this.figure = figure;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getFieldColor()
    {
        return  fieldColor;
    }

    public void setFieldColor(Color fieldColor) {
        this.fieldColor = fieldColor;
    }

    public void setGap(boolean gap)
    {
        this.gap = gap;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
