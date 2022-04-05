package com.example.kronosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;

public class WelcomeActivity extends AppCompatActivity {

    private Button changeActivityButton;
    private Button changeActivityButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        changeActivityButton = (Button) findViewById(R.id.signUpButton);
        changeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpActivity();
            }
        });

        changeActivityButton2 = (Button) findViewById(R.id.logInButton);
        changeActivityButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogInActivity();
            }
        });

    }

    public void openSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void openLogInActivity() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}