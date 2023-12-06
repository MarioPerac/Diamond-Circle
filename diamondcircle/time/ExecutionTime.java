package diamondcircle.time;

import diamondcircle.game.Game;
import java.util.logging.Level;

public class ExecutionTime extends Thread{
    private long start;
    private long end;
    private long execution;
    private boolean stopExecution = false;

    @Override
    public void run()
    {
        start  = System.nanoTime();

        while(!stopExecution)
        {
            end = System.nanoTime();
            execution = (end / 1_000_000_000) - (start/ 1_000_000_000);
            try {
                sleep(1000);
            }catch(InterruptedException e)
            {
                Game.logger.log(Level.WARNING, e.toString());
            }
        }
    }

    public long getStartInSeconds()
    {
        return start / 1_000_000_000;
    }

    public long getEndInSeconds()
    {
        return end / 1_000_000_000;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getExecution() {
        return execution;
    }

    public void setStopExecution(boolean stopExecution) {
        this.stopExecution = stopExecution;
    }
}

