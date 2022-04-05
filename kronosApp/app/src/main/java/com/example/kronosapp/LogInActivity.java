package com.example.kronosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private Button logInButton;
    private EditText inpEmail;
    private EditText  inpPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logInButton = findViewById(R.id.logIn);
        inpEmail = findViewById(R.id.inpEmail);
        inpPassword = findViewById(R.id.inpPassword);

        mAuth = FirebaseAuth.getInstance();

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInUser();
            }
        });
    }

    private void logInUser(){
        String email = inpEmail.getText().toString();
        String password = inpPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            inpEmail.setError("Please fill the email");
            inpEmail.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            inpPassword.setError("Please fill the password");
            inpPassword.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LogInActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
                    }
                    else{
                        Toast.makeText(LogInActivity.this, "log in error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}