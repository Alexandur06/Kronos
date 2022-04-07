package com.example.kronosapp.calendar;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event
{
    private String name = null;
    private LocalDate date = null;
    private LocalTime time = null;

    public Event(){}

    public Event(String name, String date, String time)
    {
        this.name = name;
        this.date = CalendarUtils.formattedStringToDate(date);
        this.time = CalendarUtils.formattedStringToTime(time);
    }

    public Event(String name, LocalDate date, LocalTime time)
    {
        this.name = name;
        this.date = date;
        this.time = time;
    }


    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            if(event.getLocalDateDate().equals(date))
                events.add(event);
        }

        return events;
    }

    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if(event.getLocalDateDate().equals(date) && eventHour == cellHour)
                events.add(event);
        }

        return events;
    }

    public String getName()
    {
        return name;
    }

    public String getDate(){return  CalendarUtils.formattedDate(date);}

    public String getTime(){return CalendarUtils.formattedTime(time);}

    public LocalDate getLocalDateDate()
    {
        return date;
    }

    public LocalTime getLocalDateTime()
    {
        return time;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }
}