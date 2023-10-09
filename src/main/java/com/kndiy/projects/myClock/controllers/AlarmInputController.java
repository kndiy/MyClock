package com.kndiy.projects.myClock.controllers;

import com.kndiy.projects.myClock.models.AlarmModel;
import com.kndiy.projects.myClock.views.AlarmInputView;
import com.kndiy.projects.myClock.views.BasedJFrame;
import com.kndiy.projects.myClock.views.ClockView;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TreeSet;

public class AlarmInputController {

    private String alarmInput;
    private final TreeSet<LocalDateTime> alarmSet;
    private final ClockView clockView;
    private final AlarmModel alarmModel;
    public AlarmInputController(BasedJFrame basedJFrame, TreeSet<LocalDateTime> alarmSet, ClockView clockView) {

        this.alarmSet = alarmSet;
        this.clockView = clockView;
        this.alarmModel = new AlarmModel();

        new AlarmInputView(this);

        operate();
    }

    private void operate() {

        if (alarmInput != null) {
            alarmModel.checkAlarmInput();
            alarmModel.addAlarmInputToAlarmSet(alarmInput, alarmSet);
            alarmModel.populateAlarmList(alarmSet, clockView.getAlarmList());
        }
    }

    public void setAlarmInput(String alarmInput) {
        this.alarmInput = alarmInput;
    }
}
