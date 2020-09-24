package com.example.aigeth.ludoonline.ConnectionHandler;

import android.os.AsyncTask;
import java.io.DataOutputStream;
import java.io.IOException;


public class SendHandler extends AsyncTask<Void, Void, Void> {
    private DataOutputStream dout;
    private String command;

    public SendHandler(DataOutputStream dout) {
        this.dout = dout;
    }


    public void sendFacebookLoginDetails(long id, String firstName, String lastName, String url){
        command = ("Login_Facebook\\n" + id + "£" + firstName + "£" + lastName + "£" + url);
        this.execute();
    }

    public void sendLogOut(){
        command = ("LogOut\\n");
        this.execute();
    }

    public void getAllLobbies(String name){
        command = ("Lobby_GetAll\\n" + name);
        this.execute();
    }

    public void rollDice(){
        command = ("DiceRolled\\n");
        this.execute();
    }

    public void createLobby(String name, String password){
        command = ("Lobby_Create\\n" + name  + "£" + password);
        this.execute();
    }

    public void getAllUsers(){
        command = ("GetAllUsers\\n");
        this.execute();
    }

    public void joinLobby(int id){
        command = ("Lobby_Join\\n" + id);
        this.execute();
    }

    public void leaveLobby(){
        command = ("BackPressed\\n");
        this.execute();
    }

    public void ready(){
        command = ("ready\\n");
        this.execute();
    }

    public void kickPlayer(int id){
        command = ("Kick\\n" + id);
        this.execute();
    }

    public void piecePicked(int id){
        command = ("PiecePicked\\n" + id);
        this.execute();
    }

    public void AnimationDone(int id){
        command = ("AnimationDone\\n" + id);
        this.execute();
    }

    public void sendGuestLoginDetails(String firstName, String lastName){
        command = ("Login_Guest\\n" + firstName  + "£" + lastName);
        this.execute();
    }

    public void sendJoinPassWordLobby(int id, String password){
        command = ("Lobby_Join_Password\\n" + id + "£" + password);
        this.execute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            dout.writeUTF(command);
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                dout.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }catch (NullPointerException e){

        }
        return null;
    }

}
