package com.kndiy.projects.myClock.models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class GetTimeModel {

    private final DateTimeFormatter timeFormatter;
    private final DateTimeFormatter dateFormatter;
    private final DateTimeFormatter weekdayFormatter;

    public GetTimeModel() {
        timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        dateFormatter = DateTimeFormatter.ofPattern("MMMM dd");
        weekdayFormatter = DateTimeFormatter.ofPattern("EEEE");
    }

    public String durationToString(Duration duration) {
        long millis = duration.toMillis();
        long milliSec = millis % 100;

        long secs = duration.getSeconds();
        long second = secs % 60;
        long min = secs / 60 % 60;
        long hour = secs / 60 / 60 % 60;

        return String.format("%02d:%02d:%02d:%02d", hour, min, second, milliSec);
    }

    public Duration stringToDuration(String durationString) {

        String[] stringArray = durationString.split(":");
        long hour = Long.parseLong(stringArray[0]);
        long min = Long.parseLong(stringArray[1]);
        long sec = Long.parseLong(stringArray[2]);
        long milli = Long.parseLong(stringArray[3]);

        long millis = hour * 3600 * 100 + min * 60 * 100 + sec * 100 + milli;

        return Duration.of(millis, ChronoUnit.MILLIS);
    }

    public String getCurrentTime() {
        return timeFormatter.format(LocalDateTime.now());
    }



    public String getCurrentDate() {
        return dateFormatter.format(LocalDate.now());
    }

    public String getCurrentWeekday() {
        return weekdayFormatter.format(LocalDate.now());
    }

}
