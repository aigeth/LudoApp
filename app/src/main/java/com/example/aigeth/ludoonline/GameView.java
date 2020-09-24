package com.example.aigeth.ludoonline;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.aigeth.ludoonline.model.AnimationInterface;
import com.example.aigeth.ludoonline.model.Board;
import com.example.aigeth.ludoonline.model.Point;

public class GameView extends View {

    private Bitmap background;
    private static final String TAG = "GameView";
    public Board board;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    public void init(Context context){
        board = Board.getInstance(context);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.ludo_game);
        new Animator().start();
    }

    private class Animator extends Thread{

        @Override
        public void run(){
            while (true){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, 0,0, null);

        for(AnimationInterface animation : board.getAnimations()){
            Point point = animation.Animate(getWidth(), getHeight());
            canvas.drawBitmap(point.getImage(), (float) point.getX(), (float) point.getY(), null);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                return true;
            }

            case MotionEvent.ACTION_MOVE: {
                return true;
            }

            case MotionEvent.ACTION_UP: {
                double x = event.getX()/this.getWidth(), y = event.getY()/this.getHeight();

                for(AnimationInterface animation : board.getAnimations()){
                    if(animation.isTouching(x, y, getWidth(), getHeight()))
                        break;
                }

                return true;
            }

        }
        return false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        background = getResizedBitmap(background, w, h);

    }

    private Bitmap getResizedBitmap(Bitmap bitmap, int width, int height){
        Matrix matrix = new Matrix();

        RectF src = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF dest = new RectF(0, 0, width, height);

        matrix.setRectToRect(src, dest, Matrix.ScaleToFit.FILL);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

}
