package com.example.aigeth.ludoonline.model;

import android.graphics.Bitmap;

public class Point {
    private Bitmap pieceImage;
    private double x, y;

    public Point(Bitmap pieceImage, double x, double y) {
        this.pieceImage = pieceImage;
        this.x = x;
        this.y = y;
    }

    public Bitmap getImage() {
        return pieceImage;
    }

    public void setImage(Bitmap pieceImage) {
        this.pieceImage = pieceImage;
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
}
