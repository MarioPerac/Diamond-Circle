package diamondcircle.card;

import diamondcircle.game.Game;

import java.util.LinkedList;
import java.util.Random;
import java.util.Collections;
import java.util.logging.Level;

public class DeckOfCards extends Thread {
    private LinkedList<Card> cards = new LinkedList<>();
    public static Card PULLED_CARD;

    public DeckOfCards()
    {
        for(int i=1, value = 1; i <=40; i++)
        {
            cards.add(new OrdinaryCard(value));

            if(i%10 == 0)
            {
                value++;
            }
        }

        Random random = new Random();

        for(int i=1; i<=12; i++)
        {
            cards.add(new SpecialCard(random.nextInt(SpecialCard.maxNumGaps) + 1));
        }

        Collections.shuffle(cards);
        PULLED_CARD = new OrdinaryCard();
		
		this.setDaemon(true);
    }

    private void pullCard()
    {
        PULLED_CARD = cards.pollFirst();
        cards.addLast(PULLED_CARD);
    }

    public void run()
    {
        while(!Game.GAME_OVER)
        {	
            
			  synchronized (this){
				  pullCard();
                try {
                    wait();
                } catch (InterruptedException e) {
                    Game.logger.log(Level.WARNING, e.toString());
                }
			  }
		}

	}
}
