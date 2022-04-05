package com.example.kronosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GroupChatActivity extends AppCompatActivity {
    private Button changeToCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        changeToCalendar = (Button) findViewById(R.id.changeToCalendar);
        changeToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendarActivity();

            }
        });
    }

    public void openCalendarActivity() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}