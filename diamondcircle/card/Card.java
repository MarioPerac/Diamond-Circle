package diamondcircle.card;

import javax.swing.ImageIcon;

public abstract class Card{

    protected int value;
    protected ImageIcon cardImage;
    public static int x = 25;
    public static int y = 80;

    public Card(int value)
    {
        this.value = value;
    }

    public Card(int value, ImageIcon cardImage)
    {
        this.value = value;
        this.cardImage = cardImage;
    }

    public int getValue()
    {
        return value;
    }

    public ImageIcon getCardImage()
    {
        return cardImage;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public void setCardImage(ImageIcon cardImage)
    {
        this.cardImage = cardImage;
    }

}
