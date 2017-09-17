package com.example.nisha.lumohacks2017;

/**
 * Created by nisha on 2017-09-17.
 */

public class UserInformation {

    public int hourInBed;
    public int minuteInBed;
    public int tryHour;
    public int tryMinute;
    public int sleepHour;
    public int sleepMinute;
    public int numberAwake;
    public int hoursAsleep;
    public int minutesAsleep;
    public int awakeHour;
    public int awakeMinute;
    public int hourOutBed;
    public int minuteOutBed;
    public String comments;
    public int minsInBed;
    public int efficiency;
    public int month;
    public int day;
    public int year;

    public UserInformation() {
    }

    public UserInformation(int hourInBed, int minuteInBed, int tryHour, int tryMinute, int sleepHour, int sleepMinute, int numberAwake, int hoursAsleep,
                           int minutesAsleep, int awakeHour, int awakeMinute, int hourOutBed, int minuteOutBed, String comments, int minsInBed, int efficiency, int month, int day, int year) {
        this.hourInBed = hourInBed;
        this.minuteInBed = minuteInBed;
        this.tryHour = tryHour;
        this.tryMinute = tryMinute;
        this.sleepHour = sleepHour;
        this.sleepMinute = sleepMinute;
        this.numberAwake = numberAwake;
        this.hoursAsleep = hoursAsleep;
        this.minutesAsleep = minutesAsleep;
        this.awakeHour = awakeHour;
        this.awakeMinute = awakeMinute;
        this.hourOutBed = hourOutBed;
        this.minuteOutBed = minuteOutBed;
        this.comments = comments;
        this.minsInBed = minsInBed;
        this.efficiency = efficiency;
        this.month = month;
        this.day = day;
        this.year = year;

    }


}
