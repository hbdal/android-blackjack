package dk.kea.class2015.hjalte.BlackJack;

import java.util.List;

/**
 * Created by Hjalte on 25-09-2015.
 */
public class Player
{
    public int calculateHand(List<Card> hand)
    {
        int handScore = 0;
        int numOfAces = 0;
        int thisCardValue = 0;
        int size = hand.size();
        for(int i = 0; i < size; i++)
        {
            thisCardValue = hand.get(i).getRank();
            if(thisCardValue == 11) numOfAces++;
            handScore = handScore + thisCardValue;
        }
        while(handScore > 21 && numOfAces > 0)
        {
            handScore = handScore - 10;
            numOfAces--;
        }
        return handScore;
    }
}
