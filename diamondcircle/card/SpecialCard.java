package diamondcircle.card;

import  javax.swing.ImageIcon;
import java.io.File;

public class SpecialCard extends  Card{
    public static final int maxNumGaps = 5;

    public SpecialCard(int value)
    {
        super(value,new ImageIcon("diamondcircle" + File.separator +"card" + File.separator + "SpecialCard.png"));
    }

    @Override
    public String toString()
    {
        return "SpecialCard value=" + value;
    }

}
