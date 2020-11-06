package com.example.memeapp;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class customize {
    private String mag,area;
     String url;
    long time;
    customize(String mag,String area,long time,String url)
    {
        this.mag=mag;
        this.area=area;
        this.time=time;
        this.url=url;
    }

    public String getArea() {
        return area;
    }

    public String getMag() {
        return mag;
    }

    public long getTime() {
        return time;
    }
    public String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    public String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    public  String getUrl() {
        return url;
    }
}
