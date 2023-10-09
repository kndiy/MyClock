package com.kndiy.projects.myClock.models;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeSet;

public class AlarmModel {

    private Clip alarmClip;
    public void playAlarmSound() {

        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(this.getClass().getResourceAsStream("/audio/air_raid_alert.wav")));
            alarmClip = AudioSystem.getClip();
            alarmClip.open(inputStream);
            alarmClip.start();
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopAlarmSound() {
        alarmClip.stop();
    }

    public void checkAlarmInput() {

    }

    public void addAlarmInputToAlarmSet(String alarmInput, TreeSet<LocalDateTime> alarmSet) {

        String[] input = alarmInput.split(":");

        LocalDateTime time = LocalDateTime.now();
        time = time.withHour(Integer.parseInt(input[0]));
        time = time.withMinute(Integer.parseInt(input[1]));
        time = time.withSecond(Integer.parseInt(input[2]));

        if (time.isBefore(LocalDateTime.now())) {
            time = time.plusDays(1);
        }
        alarmSet.add(time);

        if (alarmSet.size() > 3) {
            alarmSet.pollLast();
        }
    }

    public void populateAlarmList(TreeSet<LocalDateTime> alarmSet, ArrayList<JLabel> alarmList) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

        int i = 0;
        for (LocalDateTime alarm : alarmSet) {
            alarmList.get(i).setText(dtf.format(alarm));
            i++;
        }

        while (i < 3) {
            alarmList.get(i).setText("__:__:__");
            i++;
        }
    }
}
