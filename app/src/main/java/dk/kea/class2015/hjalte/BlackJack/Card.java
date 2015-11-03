package dk.kea.class2015.hjalte.BlackJack;

import android.graphics.Bitmap;

/**
 * Created by Hjalte pc on 18-09-2015.
 */
public class Card
{
    private int id;
    private Bitmap bmp;
    private int suit;
    private int rank;

    public Card(int newId)
    {
        id = newId;
        suit = Math.round(id / 100) * 100;
        rank = id - suit;
        if(rank >= 11 && rank <= 13) rank = 10;
        if(rank == 14) rank = 11;
    }

    public int getId()
    {
        return id;
    }

    public int getSuit()
    {
        return suit;
    }

    public int getRank()
    {
        return rank;
    }

    public void setBitMap(Bitmap newBmp)
    {
        bmp = newBmp;
    }

    public Bitmap getBitmap()
    {
        return bmp;
    }
}
