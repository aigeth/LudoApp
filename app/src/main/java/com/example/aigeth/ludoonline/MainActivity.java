package com.example.aigeth.ludoonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aigeth.ludoonline.ConnectionHandler.ClientConnection;
import com.example.aigeth.ludoonline.ConnectionHandler.CommandHandler;
import com.example.aigeth.ludoonline.ConnectionHandler.SendHandler;
import com.example.aigeth.ludoonline.GameLobby.GameLobbies;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements CommandHandler {
    CallbackManager callbackManager = CallbackManager.Factory.create();
    private static final String EMAIL = "email";
    private static final String TAG = "MainActivity";
    private EditText guestName;
    LoginButton loginButton;
    ClientConnection clientConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        guestName = (EditText) findViewById(R.id.guestName);
        guestName.setText("Guest_" + (int)(Math.random() * 1000000));
        guestName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    SendHandler sendHandler = new SendHandler(clientConnection.getDout());
                    String name[] = guestName.getText().toString().split("_");
                    sendHandler.sendGuestLoginDetails(name[0], name[1]);
                    GameLobbies.getInstance().setGuestName(guestName.getText().toString());
                    return true;
                }
                return false;
            }
        });

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL, "public_profile"));

        clientConnection = ClientConnection.getInstance(this);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(isLoggedIn){
            requestLoginDetails();
        }

        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        Log.d(TAG, "onCreate: ");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: ");
                requestLoginDetails();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG, "onError: " + exception.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        clientConnection = ClientConnection.getInstance(this);
        LoginManager.getInstance().logOut();
        SendHandler sendHandler = new SendHandler(clientConnection.getDout());
        sendHandler.sendLogOut();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clientConnection.close();
    }

    public void requestLoginDetails(){
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            private static final String TAG = "MainActivity";
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d(TAG, "onCompleted: ");
                sendUserUInfo(object);
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    public void sendUserUInfo(JSONObject object){
        String  id = "", first_name = "", last_name = "", picture = "";
        Log.d(TAG, "displayUserUInfo: Before try");
        try {
            id = object.getString("id");
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            picture = "http://graph.facebook.com/"+id+"/picture?type=square";

        } catch (JSONException e) {
            e.printStackTrace();
        }

        GameLobbies.getInstance().setGuestName(first_name + " " + last_name);
        SendHandler sendHandler = new SendHandler(clientConnection.getDout());
        sendHandler.sendFacebookLoginDetails(Long.parseLong(id), first_name, last_name, picture);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void handle(String string) {
        String[] command = string.split("\\n");
        switch (command[0]) {
            case "Lobby_General":
                Log.d(TAG, "handle: Lobby_General");
                Intent intent = new Intent(MainActivity.this, LobbyActivity.class);
                startActivity(intent);
                break;

           /* case "Board":
                client.initAll(Integer.parseInt(subString[1]));
                break;
            case "Color":
                client.recieveColor(Integer.parseInt(subString[1]));
                break;
            case "Dice":
                client.dice.stopRolling(subString[1]);
                break;
            case "diceRolling":
                client.dice.rollAni();
                break;
            case "Status":
                client.pieces.updateStatus(Integer.parseInt(subString[1]), Integer.parseInt(subString[2]), Integer.parseInt(subString[3]));
                break;
            case "reconnected":
                client.pieces.reconnected(Integer.parseInt(subString[1]), Integer.parseInt(subString[2]), Integer.parseInt(subString[3]));
                break;
            case "Name":
                client.names[Integer.parseInt(subString[2])] = subString[1];
                break;
            case "Finished":
                client.playFinished();
                break;
            case "Alive":
                break;
            case "MUTE":
                client.mute();
                break;
            case "sendText":
                client.getText(subString[1] + ": " + subString[2]);
                break;
            case "commandReply":
                client.getText(subString[1]);
                break;
            case "PickAPiece":
                if(Integer.parseInt(subString[1]) == client.pieces.myColor){
                    client.youChoose();
                }
                break;
            case "RollTheDice":
                client.dice.setRollable(Integer.parseInt(subString[1]) == client.pieces.myColor);

                client.setTurn(Integer.parseInt(subString[1]));
                break;*/
            default:
                break;
        }
    }

    @Override
    public void makeToast(String string) {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
