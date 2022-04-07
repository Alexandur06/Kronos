package com.example.kronosapp.calendar;
import static com.example.kronosapp.calendar.CalendarUtils.daysInWeekArray;
import static com.example.kronosapp.calendar.CalendarUtils.monthYearFromDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kronosapp.R;
import com.example.kronosapp.calendar.CalendarAdapter;
import com.example.kronosapp.calendar.CalendarUtils;
import com.example.kronosapp.calendar.DailyCalendarActivity;
import com.example.kronosapp.calendar.Event;
import com.example.kronosapp.calendar.EventAdapter;
import com.example.kronosapp.calendar.EventEditActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    DatabaseReference db = FirebaseDatabase.getInstance("https://kronos-app-tues-default-rtdb.europe-west1.firebasedatabase.app").getReference();
    ArrayList<Event> dailyEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView();

        dailyEvents = new ArrayList<>();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);

//        int c = 0;

        db.child("calendars").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dailyEvents.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
//                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    String name = postSnapshot.child("name").getValue(String.class);
                    String date = postSnapshot.child("date").getValue(String.class);
                    String time = postSnapshot.child("time").getValue(String.class);
//                    Event event = postSnapshot.getValue(Event.class);
                    dailyEvents.add(new Event(name,
                            CalendarUtils.formattedStringToDate(date),
                            CalendarUtils.formattedStringToTime(time)));
//                    System.out.println("###################################");
//                    System.out.println(name+date+time);

                    EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
                    eventListView.setAdapter(eventAdapter);

//                    dailyEvents.add(event);
                }
//                dailyEvents.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view)
    {
//        db.child("calendars").child("calendar-1").setValue(new User("eventName", "CalendarUtils.selectedDate", "12:00"));
        startActivity(new Intent(this, EventEditActivity.class));
    }

    public void dailyAction(View view)
    {
        startActivity(new Intent(this, DailyCalendarActivity.class));
    }
}