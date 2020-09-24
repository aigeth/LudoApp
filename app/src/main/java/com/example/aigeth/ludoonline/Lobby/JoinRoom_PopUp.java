package com.example.aigeth.ludoonline.Lobby;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.aigeth.ludoonline.ConnectionHandler.SendHandler;
import com.example.aigeth.ludoonline.R;

public class JoinRoom_PopUp extends AppCompatActivity {
    EditText lobbyPassword;
    Button joinPasswordButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_lobby_window);
        Intent intent = getIntent();
        int lobbyID = Integer.parseInt(intent.getStringExtra("message"));

        lobbyPassword = (EditText) findViewById(R.id.joinLobbyPassword);

        lobbyPassword.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    sendBackMessage(lobbyID, lobbyPassword.getText().toString());
                    return true;
                }
                return false;
            }
        });

        joinPasswordButton = (Button) findViewById(R.id.joinPasswordButton);

        joinPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBackMessage(lobbyID, lobbyPassword.getText().toString());
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int) (width), (int) (height*.4));
        getWindow().setGravity(Gravity.TOP|Gravity.START);


    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onPause()
    {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onPause();
        Intent i = new Intent();
        setResult(RESULT_CANCELED, i);
        finish();
    }

    public void sendBackMessage(int password, String string){
        Intent i = new Intent();
        i.putExtra("message", new String[] { "" + password, string});
        setResult(RESULT_OK, i);
        finish();
    }

}
