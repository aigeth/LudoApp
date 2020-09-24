package com.example.aigeth.ludoonline.model;

public interface AnimationInterface {
    public Point Animate(int screenWidth, int screenHeight);
    public boolean isTouching(double x, double y, int screenWidth, int screenHeight);
}
