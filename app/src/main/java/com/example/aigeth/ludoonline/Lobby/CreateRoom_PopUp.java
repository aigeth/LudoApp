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

import com.example.aigeth.ludoonline.GameLobby.GameLobbies;
import com.example.aigeth.ludoonline.R;

public class CreateRoom_PopUp extends AppCompatActivity {
    private static final String TAG = "CreateRoom_PopUp";

    CheckBox checkBox;
    EditText lobbyName, password;
    Button createButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_lobby_window);


        checkBox = (CheckBox) findViewById(R.id.passwordCheckBox);
        lobbyName = (EditText) findViewById(R.id.createRoomName);
        password = (EditText) findViewById(R.id.lobbyPassword);

        lobbyName.setText(GameLobbies.getInstance().getGuestName());

        disableEditText(password);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    enableEditText(password);
                }else{
                    disableEditText(password);
                }
            }
        });

        createButton = (Button) findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    String string = "" + password.getText().toString();
                    if(string.equals(""))
                        return;
                    sendBackMessage(password.getText().toString());
                    finish();
                }else{
                    sendBackMessage("-");
                    finish();
                }
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int) (width), (int) (height*.4));
        getWindow().setGravity(Gravity.TOP|Gravity.START);


    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
    }

    private void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
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
        Log.d(TAG, "onPause: Activity Cancelled");
    }

    public void sendBackMessage(String password){
        Intent i = new Intent();
        i.putExtra("message", new String[] { lobbyName.getText().toString(), password});
        setResult(RESULT_OK, i);
    }

}
