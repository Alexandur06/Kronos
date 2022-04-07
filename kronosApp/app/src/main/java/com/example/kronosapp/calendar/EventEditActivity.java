package com.example.kronosapp.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kronosapp.calendar.HourEvent;
import com.example.kronosapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class EventEditActivity extends AppCompatActivity
{
    private String eventNameFinal;
    private EditText eventNameET;
    private TextView eventDateTV;
    private EditText eventTimeET;

    private LocalTime time;

    DatabaseReference db = FirebaseDatabase.getInstance("https://kronos-app-tues-default-rtdb.europe-west1.firebasedatabase.app").getReference();
    ArrayList<HourEvent> eventList;

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

        //firebase listeners
//        eventList = new ArrayList<>();
//        db.child("calendars").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                eventList.clear();
//                for(DataSnapshot postSnapshot : snapshot.getChildren()){
//                    HourEvent currentEvent = postSnapshot.getValue(HourEvent.class);
////                    assert currentUser != null;
////                    if(!Objects.requireNonNull(mAuth.getCurrentUser()).getUid().equals(currentUser.getUid())){
//                    eventList.add(currentEvent);
////                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }


    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeET = findViewById(R.id.editTime);
    }

    public void saveEventAction(View view)
    {
//        String eventName = eventNameET.getText().toString();
//        String eventTime = eventTimeET.getText().toString();
//        time = LocalTime.parse(eventTime);
//        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
//        Event.eventsList.add(newEvent);

        //save to FireBase//save to FireBase
//        db.child("calendars").child("calendar-1").setValue(newEvent);
//        db.child("calendars").child("calendar-1").setValue(new User(eventName, CalendarUtils.formattedDate(CalendarUtils.selectedDate), CalendarUtils.formattedTime(LocalTime.parse(eventTime))));
        //save to FireBase//save to FireBase

        finish();

//        notificationSend(eventName, CalendarUtils.selectedDate, time);
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