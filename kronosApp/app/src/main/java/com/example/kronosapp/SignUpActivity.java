package com.example.kronosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {
    private Button signUpFireBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpFireBase = (Button) findViewById(R.id.signUpFireBase);
        signUpFireBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpFirebase();
                openMainActivity();
            }
        });
    }

    public void SignUpFirebase() {
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}