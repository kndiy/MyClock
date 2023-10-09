package com.kndiy.projects.myClock.controllers;

import com.kndiy.projects.myClock.models.AlarmModel;
import com.kndiy.projects.myClock.views.TurnOffAlarmView;

public class TurnOffAlarmController {

    private boolean alarmPlaying;
    private final AlarmModel alarmModel;
    public TurnOffAlarmController(AlarmModel alarmModel) {

        this.alarmModel = alarmModel;
        alarmPlaying = true;

        new Thread(new PlayAlarmSound()).start();

        new TurnOffAlarmView();
        alarmModel.stopAlarmSound();
        alarmPlaying = false;
    }

    private class PlayAlarmSound implements Runnable {

        @Override
        public void run() {
            while (alarmPlaying) {
                alarmModel.playAlarmSound();

                try {
                    Thread.sleep(22000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("alarm thread stopped!");
        }
    }
}
