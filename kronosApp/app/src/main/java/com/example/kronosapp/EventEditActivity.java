package com.example.kronosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.os.IResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity
{
    private String eventNameFinal;
    private EditText eventNameET;
    private TextView eventDateTV;
    private EditText eventTimeET;

    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        //time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //CharSequence name = getString(R.string.channel_name);
                //String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", "name", NotificationManager.IMPORTANCE_DEFAULT);
            //channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeET = findViewById(R.id.editTime);
    }

    public void saveEventAction(View view)
    {

        //save to FireBase//save to FireBase
        //save to FireBase//save to FireBase

        String eventName = eventNameET.getText().toString();
        String eventTime = eventTimeET.getText().toString();
        time = LocalTime.parse(eventTime);
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();

        notificationSend(eventName, CalendarUtils.selectedDate, time);
    }

    public void notificationSend(String eventName, LocalDate selectedDate, LocalTime time){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Event is due soon")
                .setContentText(eventName + " is on " + selectedDate.toString() + " at " + time.toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }


}