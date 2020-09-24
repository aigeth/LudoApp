package com.example.aigeth.ludoonline.GameLobby;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

public class GameLobbies {
    ArrayList<Lobby_Game> lobbyGames;
    private String guestName;

    private static GameLobbies theModel = null;

    private GameLobbies() {
        lobbyGames = new ArrayList<>();}

    public static GameLobbies getInstance(){
        if (theModel == null)
            theModel = new GameLobbies();
        return theModel;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public synchronized ArrayList<Lobby_Game> searchByName(String name){
        ArrayList<Lobby_Game> tmpLobbyGames = new ArrayList<>();

        for(Lobby_Game lobby_game : lobbyGames){
            if (lobby_game.getName().contains(name)){
                tmpLobbyGames.add(lobby_game);
            }
        }
        return tmpLobbyGames;
    }

    public synchronized Lobby_Game getAccountById(int id){

        for(Lobby_Game lobbyGame : lobbyGames){
            if(lobbyGame.getId() == id){
                return lobbyGame;
            }
        }

        return null;
    }

    public synchronized Lobby_Game get(int i){
        return lobbyGames.get(i);
    }
    public synchronized boolean addLobby(Lobby_Game newLobbyGame){
        /*for(Lobby_Game lobbyGame : lobbyGames){
            if(lobbyGame.getName().equals(newLobbyGame.getName()) || lobbyGame.getId() == newLobbyGame.getId())
                return false;
        }
        */
        lobbyGames.add(newLobbyGame);
        return true;
    }

    public void clear(){
        lobbyGames.clear();
    }

    public int size(){
        return lobbyGames.size();
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
}

