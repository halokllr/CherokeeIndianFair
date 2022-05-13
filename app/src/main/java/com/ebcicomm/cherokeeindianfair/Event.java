package com.ebcicomm.cherokeeindianfair;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {


    private String eventName;
    private Date dayAndTime;
    private String location;


    public Event (String eventName, Date dayAndTime, String location) {
        this.eventName = eventName;
        this.dayAndTime = dayAndTime;
        this.location = location;
    }


    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getDayAndTime() {
        return dayAndTime;
    }
    public void setDayAndTime(Date dayAndTime) {
        this.dayAndTime = dayAndTime;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
