package com.example.kronosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button changeToGroupChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeToGroupChat = (Button) findViewById(R.id.groupChat);
        changeToGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGroupChatActivity();

            }
        });
        }

    public void openGroupChatActivity() {
        Intent intent = new Intent(this, GroupChatActivity.class);
        startActivity(intent);
    }
}