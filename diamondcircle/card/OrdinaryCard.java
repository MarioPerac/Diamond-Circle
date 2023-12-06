package diamondcircle.card;

import  javax.swing.ImageIcon;
import 	java.io.File;

public class OrdinaryCard extends Card{

    public OrdinaryCard()
    {
        super(0, new ImageIcon("diamondcircle" + File.separator + "card" + File.separator + "CardBackground.png"));
    }

   public OrdinaryCard(int value)
   {
       super(value);
       setCardImage();
   }

   private void setCardImage()
   {
       if(value == 1)
       {
           cardImage = new ImageIcon("diamondcircle"+ File.separator +"card"+ File.separator +"OrdinaryCardValue1.png");
       }else if(value == 2)
       {
           cardImage = new ImageIcon("diamondcircle"+ File.separator +"card"+ File.separator +"OrdinaryCardValue2.png");
       }
       else if(value == 3)
       {
           cardImage = new ImageIcon("diamondcircle"+ File.separator +"card"+ File.separator +"OrdinaryCardValue3.png");
       }
       else if(value == 4)
       {
           cardImage = new ImageIcon("diamondcircle"+ File.separator +"card"+ File.separator +"OrdinaryCardValue4.png");
       }

   }

    @Override
    public String toString()
    {
        return "OrdinaryCard value=" + value;
    }
}
