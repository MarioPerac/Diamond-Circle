package diamondcircle.figures;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public interface RandomFigure
{
	public static Figure getRandomFigure(Color figureColor)
    {
        Random random = new Random();
        int randomNum = random.nextInt(3);
		
        if(randomNum == 0)
        {
            return new OrdinaryFigure(figureColor);
        }
        else if(randomNum == 1)
        {
            return new HoveringFigure(figureColor);
        }
        else
        {
            return new SuperFastFigure(figureColor);
        }
    }
}