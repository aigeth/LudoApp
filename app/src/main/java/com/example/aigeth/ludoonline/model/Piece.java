/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.aigeth.ludoonline.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.aigeth.ludoonline.ConnectionHandler.ClientConnection;
import com.example.aigeth.ludoonline.ConnectionHandler.SendHandler;
import com.example.aigeth.ludoonline.GameActivity;
import com.example.aigeth.ludoonline.R;

import java.util.ArrayList;
/*import ludoonline.Board;
import ludoonline.ClientConnection;
import ludoonline.GUI;
import ludoonline.SoundPlayer;*/

/**
 *
 * @author Aigeth
 */
public class Piece implements AnimationInterface {
    private int color;
    private int basePos, finishPos, id, pos, currentPos, nextPos, startingPlace, endingPlace;
    private Bitmap pieceImage;
    private double x, y, dx, dy;
    private Board board;


    public Piece (int id, int color, int basePos, int finishPos, int startingPlace, int endingPlace, int pos, Context context){
        this.id = id;
        this.color = color;
        this.basePos = basePos;
        this.finishPos = finishPos;
        this.startingPlace = startingPlace;
        this.endingPlace = endingPlace;
        this.pos = pos;
        this.nextPos = 0;
        currentPos = pos;
        board = Board.getInstance(context);
        pieceImage = BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier("piece" + color, "drawable", context.getPackageName()));
    }

    public boolean isMoving(){
        return currentPos != pos;
    }

    public void setNextPos(){
        if(nextPos == pos){
            return;
        }
        if(currentPos == endingPlace + 3){
            nextPos = pos;
        }else if(currentPos == finishPos)
            nextPos = endingPlace;
        else if(currentPos == basePos){
            nextPos = pos;
        }else if(pos == basePos){
            nextPos = pos;
        }else if(currentPos >= endingPlace){
            nextPos++;
        }else
            nextPos = (nextPos + 1) % (60);
        setVector();
    }

    public void setVector(){
        double speed;

        double x1x2 = (x - board.boardPlace[nextPos].x) * (x - board.boardPlace[nextPos].x);
        double y1y2 = (y - board.boardPlace[nextPos].y) * (y - board.boardPlace[nextPos].y);
        speed = (Math.sqrt(x1x2 + y1y2))/15;

        if(speed> 10){
            speed = 10;
        }

        double nextX = board.boardPlace[nextPos].x;
        double nextY = board.boardPlace[nextPos].y;

        double vecX, vecY;
        vecX = nextX - x;
        vecY = nextY - y;
        double vecXY = Math.sqrt((vecX*vecX) + (vecY*vecY));
        double unitX = vecX/vecXY; double unitY = vecY/vecXY;
        dx = unitX * speed; dy = unitY * speed;

    }



    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getStartingPos() {
        return basePos;
    }

    public void setStartingPos(int startingPos) {
        this.basePos = startingPos;
    }

    public int getEndingPos() {
        return finishPos;
    }

    public void setEndingPos(int endingPos) {
        this.finishPos = endingPos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public Bitmap getPieceImage() {
        return pieceImage;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public Point Animate(int screenWidth, int screenHeight) {
        //if(!isMoving()){
            this.x = board.boardPlace[pos].x; this.y = board.boardPlace[pos].y;
            return new Point(pieceImage, (x  * screenWidth)-(pieceImage.getWidth())/2, (y * screenHeight)-(pieceImage.getHeight())/2);
        /*}else if(x == board.boardPlace[pos].x && y == board.boardPlace[pos].y) {
            SendHandler sendHandler = new SendHandler(ClientConnection.getDout());
            sendHandler.AnimationDone(id);
            currentPos = pos;
            return new Point(pieceImage, (x * screenWidth)-(pieceImage.getWidth())/2, (y * screenHeight)-(pieceImage.getHeight())/2);
        }else if(x == board.boardPlace[nextPos].x && y == board.boardPlace[nextPos].y){
            currentPos = nextPos;
        }

        setNextPos();

        x += dx;
        y += dy;
        return new Point(pieceImage, (x * screenWidth)-(pieceImage.getWidth())/2, (y * screenHeight)-(pieceImage.getHeight())/2);*/
    }

    @Override
    public boolean isTouching(double x, double y, int screenWidth, int screenHeight) {
        if(this.color != board.getUsers().getMyUser().getColor())
            return false;

        double imageWidth = ((double) pieceImage.getWidth()/2)/screenWidth;
        double imageHeight = ((double) pieceImage.getHeight()/2)/screenHeight;

        if (this.x - imageWidth < x && this.x + imageWidth > x &&
                this.y - imageHeight < y && this.y + imageHeight > y){
            SendHandler sendHandler = new SendHandler(ClientConnection.getDout());
            sendHandler.piecePicked(id);
            return true;
        }

        return false;
    }

    public void move(int pos){
        this.pos = pos;
        SendHandler sendHandler = new SendHandler(ClientConnection.getDout());
        sendHandler.AnimationDone(id);
        //setNextPos();
    }

}
