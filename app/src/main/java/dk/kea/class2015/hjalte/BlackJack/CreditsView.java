package dk.kea.class2015.hjalte.BlackJack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Hjalte on 02-10-2015.
 */
public class CreditsView extends View
{
    Context myContext;
    private int screenW;
    private int screenH;
    private float scale;
    private Paint myPaint;
    private Paint secondPaint;

    public CreditsView(Context context)
    {
        super(context);
        myContext = context;
        scale = myContext.getResources().getDisplayMetrics().density;
        myPaint = new Paint();
        myPaint.setAntiAlias(true);
        myPaint.setColor(Color.WHITE);
        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setTextSize(scale * 30);

        secondPaint = new Paint();
        secondPaint.setAntiAlias(true);
        secondPaint.setColor(Color.YELLOW);
        secondPaint.setStyle(Paint.Style.FILL);
        secondPaint.setTextAlign(Paint.Align.CENTER);
        secondPaint.setTextSize(scale * 30);
    }

    @Override
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        screenH = height;
        screenW = width;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        int textX = screenW / 2;
        int textY = screenH / 4;
        int offsetY = (int) (myPaint.getTextSize() + (scale * 5));
        canvas.drawText("KEA demo game 1", textX, textY, myPaint);
        canvas.drawText("Android intro class", textX, textY + offsetY, secondPaint);
        canvas.drawText("October 2015", textX, textY + 2 * offsetY, secondPaint);
        canvas.drawText("Black Jack", textX, textY + 3 * offsetY, myPaint);
        canvas.drawText("Author: ", textX, textY + 4 * offsetY, myPaint);
        canvas.drawText("Hjalte", textX, textY + 5 * offsetY, myPaint);
    }
}
