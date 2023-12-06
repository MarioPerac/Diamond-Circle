package diamondcircle.figures;

import javax.swing.ImageIcon;
import  java.awt.Color;
import java.io.File;

public class HoveringFigure extends Figure{

    public HoveringFigure(Color figureColor)
    {
        super(figureColor);
        type = "Hovering Figure";
        setFigureImage();
    }

    private void setFigureImage()
    {
        if(figureColor.equals(Color.RED))
        {
            figureImage = new ImageIcon("diamondcircle"+File.separator+"figures"+File.separator+"RedHoveringFigure.png");
        }
        else if(figureColor.equals(Color.GREEN))
        {
            figureImage = new ImageIcon("diamondcircle"+File.separator+"figures"+File.separator+"GreenHoveringFigure.png");
        }
        else if(figureColor.equals(Color.BLUE))
        {
            figureImage = new ImageIcon("diamondcircle"+File.separator+"figures"+File.separator+"BlueHoveringFigure.png");
        }
        else if(figureColor.equals(Color.YELLOW))
        {
            figureImage = new ImageIcon("diamondcircle"+File.separator+"figures"+File.separator+"YellowHoveringFigure.png");
        }
    }
}
