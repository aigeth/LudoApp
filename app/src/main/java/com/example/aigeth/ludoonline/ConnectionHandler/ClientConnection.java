package com.example.aigeth.ludoonline.ConnectionHandler;


import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientConnection extends Thread{

    public Socket s;
    public DataInputStream din;
    public static DataOutputStream dout;//Type:Data
    public static CommandHandler commandHandler;
    public boolean shouldRun = true;
    private static final String TAG = "ClientConnection";

    private static ClientConnection theModel = null;

    private ClientConnection(CommandHandler commandHandler){
        this.commandHandler = commandHandler;
        this.start();
    }

    public static ClientConnection getInstance(CommandHandler commandHandler){
        if (theModel == null)
            theModel = new ClientConnection(commandHandler);
        else
            ClientConnection.commandHandler = commandHandler;
        return theModel;
    }

    public void sendCommand(String command){
        try {
            dout.writeUTF(command);
            dout.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            close();
        }
    }

    public void sendDiceRolled(int color) {
        sendCommand("DiceRolled£" + color);
    }

    public void sendAnimationDone(int color, int id) {
        sendCommand("animationDone£" + color + "£" + id);
    }

    public void sendMove(int color, int id) {
        sendCommand("moveSent£" + id);
    }

    public void sendFacebookLoginDetails(long id, String firstname, String url){
        sendCommand("Login_Facebook£" + id + "£" + firstname + "£" + url);
    }

    public void endConnection() {
        try {
            Log.d(TAG, "endConnection: " + "Ending");
            String string = "404";
            Thread.sleep(10);
            dout.writeUTF(string);
            dout.flush();

        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            close();
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DataOutputStream getDout() {
        return dout;
    }

    public void run() {
        try {
            s = new Socket();
            s.connect(new InetSocketAddress("188.150.174.111", 5454), 4000);
        } catch (IOException e) {
            e.printStackTrace();
            commandHandler.makeToast("Unable to connect to server...");
            return;
        }
        try {
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while(shouldRun) {
                try {
                    while(din.available() == 0) {
                        if(!shouldRun){
                            closeConnections();
                            return;
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    String reply = din.readUTF();
                    commandHandler.handle(reply);

                } catch (IOException ex) {
                    Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
                    close();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            close();
        }
    }

    public void close() {
        shouldRun = false;
        theModel = null;
    }

    private void closeConnections() {
        endConnection();
        try {
            din.close();
            dout.close();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
