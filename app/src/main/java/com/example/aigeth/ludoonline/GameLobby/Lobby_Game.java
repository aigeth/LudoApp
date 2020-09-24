package com.example.aigeth.ludoonline.GameLobby;

public class Lobby_Game {
    private int id, size;
    private String name, url;
    private boolean hasPassword;

    public Lobby_Game(int id, String name, String url, int size, boolean hasPassword) {
        this.id = id;
        this.name = name;
        this.hasPassword = hasPassword;
        this.size = size;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean hasPassword() {
        return hasPassword;
    }

    @Override
    public String toString() {
        return id + "£" + name + "£" + url + "£" + size + "£" + hasPassword;
    }
}