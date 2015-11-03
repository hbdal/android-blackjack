package dk.kea.class2015.hjalte.BlackJack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Hjalte on 04-09-2015.
 */
public class MyView extends View{

    private Paint redPaint;
    private int circleX;
    private int circleY;
    private float radius;

    public MyView(Context context){
        super(context);

        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(true);
        circleX = 100;
        circleY = 100;
        radius = 30;
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawCircle(circleX, circleY, radius, redPaint);
    }

    public boolean onTouchEvent(MotionEvent event){
        int eventAction = event.getAction();
        int X = (int)event.getX();
        int Y = (int)event.getY();

        switch (eventAction){
            case MotionEvent.ACTION_DOWN:
                redPaint.setColor(Color.BLUE);
                break;
            case MotionEvent.ACTION_MOVE:
                redPaint.setColor(Color.BLACK);
                circleX = X;
                circleY = Y;
                break;
            case MotionEvent.ACTION_UP:
                redPaint.setColor(Color.RED);
                circleX = X;
                circleY = Y;
                break;
        }
        invalidate();
        return true;
    }
}
