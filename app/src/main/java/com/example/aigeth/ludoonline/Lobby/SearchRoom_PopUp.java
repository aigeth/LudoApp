package com.example.aigeth.ludoonline.Lobby;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.aigeth.ludoonline.R;

public class SearchRoom_PopUp extends AppCompatActivity {

    EditText lobbyName;
    Button searchButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_lobby_window);

        lobbyName = (EditText) findViewById(R.id.searchLobbyText);

        searchButton = (Button) findViewById(R.id.searchLobbyButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = "" + lobbyName.getText().toString();
                sendBackMessage(string);
                finish();

            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int) (width), (int) (height*.2));
        getWindow().setGravity(Gravity.TOP|Gravity.START);


    }


    @Override
    protected void onResume()
    {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        Intent i = new Intent();
        setResult(RESULT_CANCELED, i);
    }

    public void sendBackMessage(String name){
        Intent i = new Intent();
        i.putExtra("message", name);
        setResult(RESULT_OK, i);
    }

}
