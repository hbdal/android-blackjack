package dk.kea.class2015.hjalte.BlackJack;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import dk.kea.class2015.hjalte.fall2015.R;

public class GameView extends View {

    private Context myContext;
    private List<Card> deck = new ArrayList<>();
    private int scaledCardWidth;
    private int scaledCardHeight;
    private int screenW;
    private int screenH;
    private Bitmap cardBack;
    private List<Card> myHand = new ArrayList<>();
    private List<Card> oppHand = new ArrayList<>();
    private float scale;
    private Paint myPaint;
    private int myScore = 0;
    private int oppScore = 0;
    private boolean myTurn = true;
    private int movingCardIndex = -1; // should I move the bitmap?
    private int movingCardX;        //X-value while giving
    private int movingCardY;        //Y-value while moving
    private Player theUser;
    private Player theBank;
    private int hands = 0;
    private int wins = 0;

    public GameView(Context context) {
        super(context);
        myContext = context;
            scale = myContext.getResources().getDisplayMetrics().density;
        myPaint = new Paint();
        myPaint.setAntiAlias(true);
        myPaint.setColor(Color.WHITE);
        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(20 * scale);
        theUser = new Player();
        theBank = new Player();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        screenW = w;
        screenH = h;
        initCards();

        firstCards();
        myScore = theUser.calculateHand(myHand);
        if (myScore == 21) {
            myTurn = false;
            makeBankPlay();
        }

        scaledCardWidth = (int) (screenW / 6);
        scaledCardHeight = (int) (scaledCardWidth * 1.28);
        Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.card_back);
        cardBack = Bitmap.createScaledBitmap(tempBitmap, scaledCardWidth, scaledCardHeight, false);
    }

    protected void onDraw(Canvas canvas) {
        if (!myTurn) oppScore = theBank.calculateHand(oppHand);
        canvas.drawText("Casino's score: " + oppScore, 10 * scale, myPaint.getTextSize(), myPaint);
        myScore = theUser.calculateHand(myHand);
        canvas.drawText("My score: " + myScore, 10 * scale, screenH - 10 * scale, myPaint);

        int stop = myHand.size();
        for (int i = 0; i < stop; i++) {
            canvas.drawBitmap(myHand.get(i).getBitmap(), 20 * scale + i * (scaledCardWidth + 5), screenH - myPaint.getTextSize() - 10 * scale * 2 - scaledCardHeight, myPaint);
        }

        stop = oppHand.size();
        for (int i = 0; i < stop; i++) {
            if (i == 0 && myTurn) {
                canvas.drawBitmap(cardBack, 20 * scale + i * (scaledCardWidth + 5), myPaint.getTextSize() + 10 * scale, myPaint);
            } else {
                canvas.drawBitmap(oppHand.get(i).getBitmap(), 20 * scale + i * (scaledCardWidth + 5), myPaint.getTextSize() + 10 * scale, myPaint);
            }
        }
        for (int i = 0; i < 5; i++) {
            canvas.drawBitmap(cardBack, 20 * scale + i * 5, (screenH - scaledCardHeight) / 2, null);
        }
        if (movingCardIndex == 0)    //should we draw a moving card?
        {
            canvas.drawBitmap(cardBack, movingCardX, movingCardY, null);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();
        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                if (myTurn) {
                    if (X > 20 * scale && X < 20 * scale + (scaledCardWidth + 30) && Y > (screenH - scaledCardHeight) / 2 && Y < (screenH + scaledCardHeight) / 2) {
                        movingCardIndex = 0;
                        movingCardX = X - (int) (scaledCardWidth * 0.8);
                        movingCardY = Y - (int) (scaledCardHeight * 0.8);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //movingCardX = X;
                //movingCardY = Y;
                movingCardX = X - (int) (scaledCardWidth * 0.8);
                movingCardY = Y - (int) (scaledCardHeight * 0.8);
                break;
            case MotionEvent.ACTION_UP:
                if (movingCardIndex == 0 && Y >= screenH - myPaint.getTextSize() - 10 * scale * 4 - (scaledCardHeight * 1.02)) {
                    drawCard(myHand);
                    myScore = theUser.calculateHand(myHand);
                    if (myScore >= 21 || myHand.size() >= 5) {
                        myTurn = false;
                        makeBankPlay();
                    }
                }
                if (myTurn && movingCardIndex == -1) {
                    showDrawStopDialog();
                }
                movingCardIndex = -1;
                break;
        }
        invalidate();
        return true;
    }

    private void initCards() {
        int tempId;
        Card tempCard;
        int resourceId;
        Bitmap tempBitmap;
        Bitmap scaledBitmap;

        for (int i = 0; i < 4; i++) //Once for each card suit/color
        {
            for (int j = 102; j < 115; j++) {
                tempId = (i * 100) + j;
                tempCard = new Card(tempId);
                resourceId = getResources().getIdentifier("card" + tempId, "drawable", myContext.getPackageName());
                tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), resourceId);
                scaledCardWidth = (int) screenW / 6;
                scaledCardHeight = (int) (scaledCardWidth * 1.28);
                scaledBitmap = Bitmap.createScaledBitmap(tempBitmap, scaledCardWidth, scaledCardHeight, false);
                tempCard.setBitMap(scaledBitmap);
                deck.add(tempCard);
            }
        }
    }

    private void firstCards() {
        Collections.shuffle(deck, new Random());
        for (int i = 0; i < 2; i++) {
            drawCard(myHand);
            drawCard(oppHand);
        }
    }

    private void drawCard(List<Card> handToDraw) {
        int theLastCard = deck.size() - 1;
        handToDraw.add(deck.get(theLastCard));
        deck.remove(theLastCard);
    }

    private void showDrawStopDialog() {
        final Dialog drawStopDialog = new Dialog(myContext);
        drawStopDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        drawStopDialog.setContentView(R.layout.draw_stop_dialog);
        Button drawButton = (Button) drawStopDialog.findViewById(R.id.drawButton);
        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawCard(myHand);
                drawStopDialog.dismiss();
                Toast.makeText(myContext, "You chose to draw a card", Toast.LENGTH_SHORT).show();
                invalidate();
                myScore = theUser.calculateHand(myHand);
                if (myScore >= 21 || myHand.size() >= 5) {
                    myTurn = false;
                    makeBankPlay();
                }
            }

        });

        Button standButton = (Button) drawStopDialog.findViewById(R.id.standButton);
        standButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myTurn = false;
                drawStopDialog.dismiss();
                myScore = theUser.calculateHand(myHand);
                if (myScore <= 21) {
                    Toast.makeText(myContext, "You chose to stand", Toast.LENGTH_SHORT).show();
                }
                invalidate();
                makeBankPlay();
            }
        });
        drawStopDialog.show();

    }

    private void makeBankPlay() {
        boolean bankFinished = false;
        myScore = theUser.calculateHand(myHand);
        oppScore = theBank.calculateHand(oppHand);
        if (myScore > 21) {
            Toast.makeText(myContext, "You have " + myScore + " so you are busted!", Toast.LENGTH_LONG).show();
            bankFinished = true;
        }
        if (myScore == 21) {
            Toast.makeText(myContext, "You got 21, a BlackJack!", Toast.LENGTH_LONG).show();
        }
        if (myHand.size() >= 5 && myScore <= 21) {
            Toast.makeText(myContext, "You got 5 cards, a BlackJack!", Toast.LENGTH_LONG).show();
        }

        while (!bankFinished) {
            oppScore = theBank.calculateHand(oppHand);
            if (oppScore < 17 || (oppScore < myScore && myScore < 22)) {
                drawCard(oppHand);
            } else {
                bankFinished = true;
            }
            invalidate();
        }
        endHand();
    }

    private void endHand() {
        final Dialog endHandDialog = new Dialog(myContext);
        endHandDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        endHandDialog.setContentView(R.layout.end_hand_dialog);
        endHandDialog.setCanceledOnTouchOutside(false);
        TextView endHandText = (TextView) endHandDialog.findViewById(R.id.endHandText);
        hands++;
        if (myScore < 21 && myHand.size() >= 5) myScore = 21;
        if (myScore > oppScore && myScore < 22 || myScore < 22 && oppScore > 21) {
            endHandText.setText("You won! " + myScore + " over " + oppScore);
            wins++;
        } else {
            endHandText.setText("You lost! " + myScore + " against " + oppScore);
        }
        TextView winsHands = (TextView) endHandDialog.findViewById(R.id.winsHandText);
        winsHands.setText("You have won " + wins + " from " + hands + " hand(s)");

        Button playAgainButton = (Button) endHandDialog.findViewById(R.id.playAgainBtn);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endHandDialog.dismiss();
                initNewHand();
            }
        });
        endHandDialog.show();
    }

    private void initNewHand() {
        deck.addAll(myHand);
        deck.addAll(oppHand);
        myHand.clear();
        oppHand.clear();
        myScore = 0;
        oppScore = 0;
        firstCards();
        myTurn = true;
        invalidate();
    }
}
