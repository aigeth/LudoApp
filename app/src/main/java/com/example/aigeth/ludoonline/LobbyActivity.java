package com.example.aigeth.ludoonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aigeth.ludoonline.ConnectionHandler.ClientConnection;
import com.example.aigeth.ludoonline.ConnectionHandler.CommandHandler;
import com.example.aigeth.ludoonline.ConnectionHandler.SendHandler;
import com.example.aigeth.ludoonline.Lobby.CreateRoom_PopUp;
import com.example.aigeth.ludoonline.GameLobby.GameLobbies;
import com.example.aigeth.ludoonline.Lobby.JoinRoom_PopUp;
import com.example.aigeth.ludoonline.Lobby.LobbyListAdapter;
import com.example.aigeth.ludoonline.GameLobby.Lobby_Game;
import com.example.aigeth.ludoonline.Lobby.SearchRoom_PopUp;

public class LobbyActivity extends AppCompatActivity implements CommandHandler {
    private static final String TAG = "LobbyActivity";

    public static LobbyListAdapter adapter;
    public static SwipeRefreshLayout mSwipeRefreshLayout;
    public ClientConnection clientConnection;
    private GameLobbies gameLobbies;
    private String searchString;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        initRecyclerView();
        gameLobbies = GameLobbies.getInstance();
        searchString = "";

        final ImageView createButton = (ImageView) findViewById(R.id.createRoomImage);
        final ImageView searchButton = (ImageView) findViewById(R.id.searchButton);

        createButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    Animation buttonAnimation = AnimationUtils.loadAnimation(LobbyActivity.this, R.anim.buttondown);
                    createButton.startAnimation(buttonAnimation);
                    return true;
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    Animation buttonAnimation = AnimationUtils.loadAnimation(LobbyActivity.this, R.anim.buttonup);
                    createButton.startAnimation(buttonAnimation);
                    startActivityForResult(new Intent(LobbyActivity.this, CreateRoom_PopUp.class), 999);
                    return true;
                }
                return false;
            }

        });

        searchButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    Animation buttonAnimation = AnimationUtils.loadAnimation(LobbyActivity.this, R.anim.buttondown);
                    searchButton.startAnimation(buttonAnimation);
                    return true;
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    Animation buttonAnimation = AnimationUtils.loadAnimation(LobbyActivity.this, R.anim.buttonup);
                    searchButton.startAnimation(buttonAnimation);
                    startActivityForResult(new Intent(LobbyActivity.this, SearchRoom_PopUp.class), 777);
                    return true;
                }
                return false;
            }

        });

    }



    public void updateView(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void initRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_lobbies);
        adapter = new LobbyListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SendHandler sendHandler = new SendHandler(clientConnection.getDout());
                sendHandler.getAllLobbies(searchString);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume(){
        Log.d(TAG, "onResume: ");
        super.onResume();
        clientConnection = ClientConnection.getInstance(this);
        SendHandler sendHandler = new SendHandler(clientConnection.getDout());
        sendHandler.getAllLobbies(searchString);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        SendHandler sendHandler = new SendHandler(clientConnection.getDout());
        sendHandler.leaveLobby();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 999){
            if(resultCode == RESULT_OK){
                SendHandler sendHandler = new SendHandler(clientConnection.getDout());
                String message[] = data.getStringArrayExtra("message");
                sendHandler.createLobby(message[0], message[1]);
            }
        }
        if(requestCode == 888){
            if(resultCode == RESULT_OK){
                SendHandler sendHandler = new SendHandler(clientConnection.getDout());
                String message[] = data.getStringArrayExtra("message");
                sendHandler.sendJoinPassWordLobby(Integer.parseInt(message[0]), message[1]);
            }
        }

        if(requestCode == 777){
            if(resultCode == RESULT_OK){
                SendHandler sendHandler = new SendHandler(clientConnection.getDout());
                searchString = data.getStringExtra("message");
                sendHandler.getAllLobbies(searchString);
            }
        }
    }

    @Override
    public void handle(String string) {
        String[] command = string.split("\\n");
        Log.d(TAG, "Lobbyhandle: " + string);
        switch (command[0]) {
            case "AllLobbies":
                gameLobbies.clear();
                for(int i = 1; i < command.length; i++){
                    String[] subMessage = command[i].split("£");
                    gameLobbies.addLobby(new Lobby_Game(Integer.parseInt(subMessage[0]), subMessage[1], subMessage[2], Integer.parseInt(subMessage[3]), Boolean.parseBoolean(subMessage[4])));
                }
                updateView();
                break;
            case "Lobby_Game":
                Intent intent = new Intent(LobbyActivity.this, GameActivity.class);
                startActivity(intent);
                break;
            case "PasswordJoinScreen":
                startActivityForResult(new Intent(LobbyActivity.this, JoinRoom_PopUp.class).putExtra("message", command[1]), 888);
                break;
            case "PasswordWrongJoinScreen":
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        makeToast("Wrong password!");
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void makeToast(String string) {
        Toast.makeText(LobbyActivity.this, string,
                Toast.LENGTH_LONG).show();
    }
}
