package com.kndiy.projects.myClock.controllers;

import com.kndiy.projects.myClock.models.AlarmModel;
import com.kndiy.projects.myClock.views.BasedJFrame;
import com.kndiy.projects.myClock.views.ClockView;
import com.kndiy.projects.myClock.models.GetTimeModel;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

public class ClockController {
    private final GetTimeModel getTimeModel;
    private final ClockView clockView;
    private final BasedJFrame basedJFrame;
    private final TreeSet<LocalDateTime> alarmSet;
    private final AlarmModel alarmModel;
    private boolean changeMode;
    public ClockController(BasedJFrame basedJFrame) {

        this.basedJFrame = basedJFrame;
        clockView = new ClockView(basedJFrame.getDEFAULT_WIDTH());
        setupClockView();

        getTimeModel = new GetTimeModel();
        alarmSet = new TreeSet<>();
        alarmModel = new AlarmModel();

        operate();
    }

    public ClockController(BasedJFrame basedJFrame, TreeSet<LocalDateTime> alarmSet) {

        this.basedJFrame = basedJFrame;
        clockView = new ClockView(basedJFrame.getDEFAULT_WIDTH());
        setupClockView();

        getTimeModel = new GetTimeModel();
        this.alarmSet = alarmSet;
        alarmModel = new AlarmModel();
        alarmModel.populateAlarmList(alarmSet, clockView.getAlarmList());

        operate();
    }

    private void setupClockView() {

        basedJFrame.getContentPane().removeAll();
        basedJFrame.repaint();

        basedJFrame.add(clockView);
        basedJFrame.setVisible(true);
        basedJFrame.pack();
    }

    private void operate() {

        Thread refreshThread = new Thread(new RefreshTime(alarmSet));
        refreshThread.start();

        JButton stopWatchModeButton = clockView.getStopWatchModeButton();
        stopWatchModeButton.addActionListener(e -> {
            changeMode = true;
            new StopWatchController(basedJFrame, alarmSet);
        });

        JButton addNewAlarmButton = clockView.getAddAlarmButton();
        addNewAlarmButton.addActionListener(e -> {
            new AlarmInputController(basedJFrame, alarmSet, clockView);
        });
    }

    private class RefreshTime implements Runnable {

        private final TreeSet<LocalDateTime> alarmSet;
        private final DateTimeFormatter dtf;

        public RefreshTime(TreeSet<LocalDateTime> alarmSet) {
            super();
            this.alarmSet = alarmSet;
            this.dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        }

        @Override
        public void run() {

            LocalDateTime currentAlarm = null;
            changeMode = false;
            boolean firstLoop = true;
            while (!changeMode) {
                String time = getTimeModel.getCurrentTime();
                String[] timeArray = time.split(":");
                String hour = timeArray[0];
                String min = timeArray[1];
                String sec = timeArray[2];

                clockView.getHourLabel().setText(hour);
                clockView.getMinuteLabel().setText(min);
                clockView.getSecondLabel().setText(sec);

                if (firstLoop || (hour.equals("00") && min.equals("00") && sec.equals("00"))) {
                    String weekday = getTimeModel.getCurrentWeekday();
                    String date = getTimeModel.getCurrentDate();

                    clockView.getWeekdayLabel().setText(weekday);
                    clockView.getDateLabel().setText(date);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                firstLoop = false;

                if (currentAlarm != null) {
                    String curAlarm = dtf.format(currentAlarm);

                    if (curAlarm.equals(time)) {

                        new TurnOffAlarmController(alarmModel);

                        alarmSet.pollFirst();
                        alarmModel.populateAlarmList(alarmSet, clockView.getAlarmList());

                        currentAlarm = null;
                    }
                }

                if (!alarmSet.isEmpty() && currentAlarm == null) {
                    currentAlarm = alarmSet.first();
                }

            }
        }
    }
}
