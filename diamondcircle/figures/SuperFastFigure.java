package diamondcircle.figures;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.io.File;
import diamondcircle.game.Game;

public class SuperFastFigure extends Figure{

    public SuperFastFigure(Color figureColor)
    {
        super(figureColor);
        type = "Super Fast Figure";
        setFigureImage();
    }
	
	public boolean move(int fieldsNum)
	{
		int nextFieldIndex = currentFieldIndex + fieldsNum;
		
		 if(nextFieldIndex == Game.PATH.size() - 2)
        {
            return false;
        }
		
		return super.move(fieldsNum);
	}

    private void setFigureImage()
    {
        if(figureColor.equals(Color.RED))
        {
            figureImage = new ImageIcon("diamondcircle"+ File.separator +"figures"+ File.separator +"RedSuperFastFigure.png");
        }
        else if(figureColor.equals(Color.GREEN))
        {
            figureImage = new ImageIcon("diamondcircle"+ File.separator +"figures"+ File.separator +"GreenSuperFastFigure.png");
        }
        else if(figureColor.equals(Color.BLUE))
        {
            figureImage = new ImageIcon("diamondcircle"+ File.separator +"figures"+ File.separator +"BlueSuperFastFigure.png");
        }
        else if(figureColor.equals(Color.YELLOW))
        {
            figureImage = new ImageIcon("diamondcircle"+ File.separator +"figures"+ File.separator +"YellowSuperFastFigure.png");
        }
    }
}
