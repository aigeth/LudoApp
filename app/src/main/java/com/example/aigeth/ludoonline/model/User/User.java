package com.example.aigeth.ludoonline.model.User;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.aigeth.ludoonline.model.Piece;

import java.util.ArrayList;

public class User {
    private long id;
    private String firstName, lastName, url;
    private static final String TAG = "User";
    private int ready;
    private final int color, startingPlace, endingPlace, basePos, finishPos;
    private ArrayList<Piece> pieces;

    public User(long id, String firstName, String lastName, String url, int color, int ready) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.url = url;
        this.color = color;

        startingPlace = (60) + (color*4);
        endingPlace = (84) + (color*4);

        basePos = color * 10;

        finishPos = ((color * 10) + 59) % 60;

        if(ready == 2)
            this.ready = Color.WHITE;
        else if(ready == 1)
            this.ready = Color.GREEN;
        else
            this.ready = Color.RED;

        pieces = new ArrayList<>();

        Log.d(TAG, "User: " + toString());
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getReady() {
        return ready;
    }

    public int getColor() {
        return color;
    }

    public void parsePiece(String string, Context context){
        String[] parsePiece = string.split(":");
        pieces.add(new Piece(Integer.parseInt(parsePiece[0]), color, basePos, finishPos, startingPlace, endingPlace, Integer.parseInt(parsePiece[1]), context));

    }

    public void upDatePiece(String string){
        String[] parsePiece = string.split(":");
        pieces.get(Integer.parseInt(parsePiece[0])).setPos(Integer.parseInt(parsePiece[1]));

    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void setReady(int ready) {
        this.ready = ready;
    }

    @Override
    public String toString() {
        return id + "£" + firstName + "£" + lastName + "£" + url;
    }




}
