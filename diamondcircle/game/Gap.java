package diamondcircle.game;

import javax.swing.ImageIcon;
import diamondcircle.figures.Figure;
import diamondcircle.figures.HoveringFigure;
import java.util.logging.Level;
import java.util.Random;

public class Gap {
	
   public static void setGapsOnPath(int numGaps)
   {
      Random random = new Random();

      while (numGaps > 0) {
         int fieldIndex = random.nextInt(Game.PATH.size() - 1);
         Field field = Game.PATH.get(fieldIndex);

         if (!field.equals(Game.MATRIX[Game.FINAL_POSITION][Game.FINAL_POSITION])
                 && !field.equals(Game.MATRIX[Game.START_ROW][Game.START_COLUMN])) {
            if (!field.isThereGap()) {
               field.setGap(true);
               numGaps--;
            }
         }
      }
   }

   public static void eatFiguresOnGap()
   {
	   
      Game.PATH.stream().forEach(field ->
      {
         Figure figure = field.getFigure();
         if(field.isThereGap())
         {
            if( figure != null && !(figure instanceof HoveringFigure)) {
               figure.setFellInGap(true);
               figure.setFinishedGame(true);
			   field.setFigure(null);
            }
            field.setGap(false);
         }
      });
   }

}
