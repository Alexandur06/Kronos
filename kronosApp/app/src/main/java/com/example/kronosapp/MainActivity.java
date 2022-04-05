package com.example.kronosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button changeToGroupChat;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeToGroupChat = (Button) findViewById(R.id.groupChat);

        mAuth = FirebaseAuth.getInstance();

        changeToGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGroupChatActivity();

            }
        });
        }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }

    public void openGroupChatActivity() {
        Intent intent = new Intent(this, GroupChatActivity.class);
        startActivity(intent);
    }
}