package com.example.aigeth.ludoonline.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.aigeth.ludoonline.ConnectionHandler.ClientConnection;
import com.example.aigeth.ludoonline.ConnectionHandler.SendHandler;

public class Dice implements AnimationInterface {
    private int dice = 0, frame = 0, maxFrameSize;
    private double x, y;
    private Bitmap[] diceImage;
    private long prevTime = 0;
    private boolean rolling = false;
    private Context context;
    private static final String TAG = "Dice";

    public Dice(double x, double y, int maxFrameSize, Context context) {
        this.x = x;
        this.y = y;
        this.maxFrameSize = maxFrameSize;
        this.context = context;
        diceImage = new Bitmap[maxFrameSize+1];
        for(int i = 0; i < 6; i++){
            diceImage[i] = BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier("dice" + i, "drawable", context.getPackageName()));
        }

    }

    @Override
    public Point Animate(int screenWidth, int screenHeight) {
        if(!rolling)
            return new Point(diceImage[dice], x * (double) screenWidth, y * (double) screenHeight);

        if(prevTime == 0){
            prevTime = System.currentTimeMillis();
        }

        if(System.currentTimeMillis() - prevTime >= 42){
            frame = ((frame + 1) % maxFrameSize);
            prevTime = System.currentTimeMillis();
        }
        return new Point(diceImage[frame], x  * (double) screenWidth, y * (double) screenHeight);
    }

    @Override
    public boolean isTouching(double x, double y, int screenWidth, int screenHeight) {
        if (this.x < x && this.x + (double) diceImage[0].getWidth()/screenWidth > x &&
        this.y < y && this.y + (double) diceImage[0].getHeight()/screenHeight > y){
            SendHandler sendHandler = new SendHandler(ClientConnection.getDout());
            sendHandler.rollDice();
            return true;
        }

        return false;
    }

    public void roll(){
        rolling = true;
    }

    public void stopRoll(){
        rolling = false;
    }

    public boolean isRolling(){
        return rolling;
    }

    public void setDice(int dice){
        this.dice = dice;
    }


}
