package com.example.aigeth.ludoonline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aigeth.ludoonline.ConnectionHandler.ClientConnection;
import com.example.aigeth.ludoonline.ConnectionHandler.CommandHandler;
import com.example.aigeth.ludoonline.ConnectionHandler.SendHandler;
import com.example.aigeth.ludoonline.model.Board;
import com.example.aigeth.ludoonline.model.User.User;
import com.example.aigeth.ludoonline.model.User.UserListAdapter;

public class GameActivity extends AppCompatActivity implements CommandHandler {
    private static final String TAG = "GameActivity";
    public static UserListAdapter adapter;
    public ClientConnection clientConnection;
    private View view;
    private Board board;
    private TextView gameStatus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        clientConnection = ClientConnection.getInstance(this);
        initRecyclerView();
        gameStatus = (TextView) findViewById(R.id.gameStatus);
        board = Board.getInstance(this);
        SendHandler sendHandler = new SendHandler(clientConnection.getDout());
        sendHandler.getAllUsers();
        //kickButton.setOnTouchListener
    }

    @Override
    public void onBackPressed() {
        SendHandler sendHandler = new SendHandler(clientConnection.getDout());
        sendHandler.leaveLobby();
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_users);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new UserListAdapter(this, clientConnection);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int position = viewHolder.getAdapterPosition();
                SendHandler sendHandler = new SendHandler(clientConnection.getDout());
                sendHandler.kickPlayer(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
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
    protected void onResume() {
        super.onResume();
        view = findViewById(R.id.customView);
    }

    @Override
    public void handle(String string) {
        String[] command = string.split("\\n");
        if(!command[0].equals("Heartbeat"))
            Log.d(TAG, "Gamehandle: " + string);
        switch (command[0]) {
            case "AllLobbyUsers":
                board.clearUsers();
                for(int i = 1; i < command.length; i++){
                    String[] subMessage = command[i].split("£");
                    User user = new User(Long.parseLong(subMessage[0]), subMessage[1], subMessage[2], subMessage[3], Integer.parseInt(subMessage[4]), Integer.parseInt(subMessage[5]));
                    if(subMessage.length > 6) {
                        Log.d(TAG, "handle: " + subMessage.length);
                        String[] pieces = subMessage[6].split("§");
                        for (int j = 0; j < pieces.length; j++)
                            user.parsePiece(pieces[j], this);
                    }
                    board.addUser(user);
                }
                updateView();
                break;
            case "Kicked":
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GameActivity.this, "You were kicked!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case "Lobby_General":
                Log.d(TAG, "handle: Lobby_General");
                finish();
                break;
            case "Color":
                board.getUsers().setMyUser(Integer.parseInt(command[1]));
                break;
            case "Your_Turn":
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gameStatus.setText("Game in progress...");
                    }
                });
                break;
            case "DiceRolling":
                board.getDice().roll();
                break;
            case "DiceStopRolling":
                board.getDice().stopRoll();
                board.getDice().setDice(Integer.parseInt(command[1]));
                break;
            case "Status":
                String[] subMessage = command[1].split("£");
                Log.d(TAG, "handle: USERS" + subMessage[0] + " : " + board.getUsers().getUserByColor(Integer.parseInt(subMessage[0])));
                board.getUsers().getUserByColor(Integer.parseInt(subMessage[0])).getPieces().get(Integer.parseInt(subMessage[1])).move(Integer.parseInt(subMessage[2]));
                break;

           /*  case "Dice":
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
        Toast.makeText(GameActivity.this, string,
                Toast.LENGTH_LONG).show();
    }
}
