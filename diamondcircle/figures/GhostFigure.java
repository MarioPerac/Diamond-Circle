package diamondcircle.figures;

import diamondcircle.game.Game;
import diamondcircle.game.Field;
import java.util.Random;
import java.util.logging.Level;

public class GhostFigure extends Thread {
    private final int MAX_NUM_BONUSES = 4;
    private Random random = new Random();

    public void run()
    {
        while(!Game.GAME_OVER)
        {
            try{
                sleep(5000);
            }catch (InterruptedException e)
            {
                Game.logger.log(Level.WARNING, e.toString());
            }

            int numFields =random.nextInt(MAX_NUM_BONUSES) + 1;
            synchronized (Game.MATRIX) {
                while (numFields > 0) {
                    int fieldIndex = random.nextInt(Game.PATH.size() - 1);
                    Field field = Game.PATH.get(fieldIndex);
                    if (field.getFigure() == null && !field.equals(Game.MATRIX[Game.FINAL_POSITION][Game.FINAL_POSITION])
                            && !field.equals(Game.MATRIX[Game.START_ROW][Game.START_COLUMN])) {
                        field.addDiamonds(random.nextInt(Game.MATRIX_DIMENSION - 1) + 2);
                        numFields--;
                    }
                }
            }
        }
    }
}
