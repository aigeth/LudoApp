package com.example.aigeth.ludoonline.GameLobby;

import android.support.v7.app.AppCompatActivity;

import com.example.aigeth.ludoonline.ConnectionHandler.CommandHandler;

public class AddUser_PopUp extends AppCompatActivity implements CommandHandler {

    CommandHandler parentCommandHandler;

    public AddUser_PopUp(CommandHandler commandHandler){
        parentCommandHandler = commandHandler;
    }

    @Override
    public void handle(String string) {

    }

    @Override
    public void makeToast(String string) {

    }
}
