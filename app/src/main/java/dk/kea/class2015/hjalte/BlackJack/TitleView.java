package dk.kea.class2015.hjalte.BlackJack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import dk.kea.class2015.hjalte.fall2015.R;

/**
 * Created by Hjalte on 04-09-2015.
 */
public class TitleView extends View{

    private Context myContext;
    private Bitmap titleGraphic;        //To hold the title screen graphic
    private int screenW;
    private int screenH;

    private Bitmap playButtonUp;
    private int buttonW;
    private int buttonH;
    private Bitmap playButtonDown;
    private boolean playButtonPressed = false;

    private Bitmap creditsButtonUp;
    private Bitmap creditsButtonDown;
    private boolean creditsButtonPressed = false;


    public TitleView(Context context){
        super(context);
        myContext = context;
        titleGraphic = BitmapFactory.decodeResource(getResources(), R.drawable.blackjack250);
        playButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_up);
        playButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_down);
        buttonW = playButtonDown.getWidth();
        buttonH = playButtonDown.getHeight();
        creditsButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.credits_button_up);
        creditsButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.credits_button_down);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH){
        super.onSizeChanged(w, h, oldW, oldH);
        screenW = w;
        screenH = h;
    }

    protected void onDraw(Canvas canvas)
    {
        //canvas.drawBitmap(titleGraphic, 0, 0, null);
        canvas.drawBitmap(titleGraphic, 10 + (screenW - titleGraphic.getWidth())/2, 40, null);

        if(playButtonPressed)
        {
            canvas.drawBitmap(playButtonDown, (screenW - buttonW)/2, 40 + titleGraphic.getHeight() + 50, null);
        }
        else
        {
            canvas.drawBitmap(playButtonUp, (screenW - buttonW)/2, 40 + titleGraphic.getHeight() + 50, null);
        }

        if(creditsButtonPressed)
        {
            canvas.drawBitmap(creditsButtonDown, (screenW - buttonW)/2, 40 + titleGraphic.getHeight() + 40 + buttonH + 40, null);
        }
        else
        {
            canvas.drawBitmap(creditsButtonUp, (screenW - buttonW)/2, 40 + titleGraphic.getHeight() + 40 + buttonH + 40, null);
        }

    }

    public boolean onTouchEvent(MotionEvent event){
        int eventAction = event.getAction();
        int X = (int)event.getX();
        int Y = (int)event.getY();

        switch (eventAction){
            case MotionEvent.ACTION_DOWN:
                if(X > (screenW - buttonW)/2 && X < (screenW - buttonW)/2 + buttonW && Y > 40 + titleGraphic.getHeight() + 50 && Y < 40 + titleGraphic.getHeight() + 50 + buttonH)
                {
                    playButtonPressed = true;
                }
                if(X > (screenW - buttonW)/2 && X < (screenW - buttonW)/2 + buttonW && Y > 40 + titleGraphic.getHeight() + 40 + buttonH + 40 && Y < 40 + titleGraphic.getHeight() + 50 + buttonH + buttonH + 40)
                {
                    creditsButtonPressed = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (playButtonPressed){
                    Intent gameIntent = new Intent(myContext, GameActivity.class);
                    myContext.startActivity(gameIntent);
                }
                if(creditsButtonPressed)
                {
                    Intent creditIntent = new Intent(myContext, CreditsActivity.class);
                    myContext.startActivity(creditIntent);
                }
                playButtonPressed = false;
                creditsButtonPressed = false;
                break;
        }

        invalidate();
        return true;
    }
}
