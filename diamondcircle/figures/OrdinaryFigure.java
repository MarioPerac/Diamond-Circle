package diamondcircle.figures;

import java.awt.Color;
import javax.swing.ImageIcon;
import java.io.File;

public class OrdinaryFigure extends Figure{

    public OrdinaryFigure(Color figureColor)
    {
        super(figureColor);
        type = "Ordinary Figure";
        setFigureImage();
    }

    private void setFigureImage()
    {
       if(figureColor.equals(Color.RED))
       {
           figureImage = new ImageIcon("diamondcircle"+ File.separator +"figures"+ File.separator +"RedOrdinaryFigure.png");
       }
       else if(figureColor.equals(Color.GREEN))
       {
           figureImage = new ImageIcon("diamondcircle"+ File.separator +"figures"+ File.separator +"GreenOrdinaryFigure.png");
       }
       else if(figureColor.equals(Color.BLUE))
       {
           figureImage = new ImageIcon("diamondcircle"+ File.separator +"figures"+ File.separator +"BlueOrdinaryFigure.png");
       }
       else if(figureColor.equals(Color.YELLOW))
       {
           figureImage = new ImageIcon("diamondcircle"+ File.separator + "figures" + File.separator + "YellowOrdinaryFigure.png");
       }
    }

}
