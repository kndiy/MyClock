package com.kndiy.projects.myClock.controllers;

import com.kndiy.projects.myClock.models.GetTimeModel;
import com.kndiy.projects.myClock.views.BasedJFrame;
import com.kndiy.projects.myClock.views.StopWatchView;

import javax.swing.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.TreeSet;

public class StopWatchController {

    private final BasedJFrame basedJFrame;
    private final StopWatchView stopWatchView;
    private final GetTimeModel getTimeModel;
    private boolean isPause;
    private int operationCount;
    private Duration currentDuration;
    private final LinkedList<String> savedLabels;
    private final TreeSet<LocalDateTime> alarmSet;

    public StopWatchController(BasedJFrame basedJFrame, TreeSet<LocalDateTime> alarmSet) {

        this.basedJFrame = basedJFrame;
        stopWatchView = new StopWatchView(basedJFrame.getDEFAULT_WIDTH());
        setupStopWatchView();

        savedLabels = new LinkedList<>();
        savedLabels.add("00:00:00:00");
        savedLabels.add("00:00:00:00");
        savedLabels.add("00:00:00:00");

        populateSavedLabels();
        operationCount = 0;

        this.getTimeModel = new GetTimeModel();
        this.alarmSet = alarmSet;

        operate();
    }

    private void populateSavedLabels() {
        int i = 2;

        for (String cur : savedLabels) {
            stopWatchView.getSavedLabels().get(i).setText(cur);
            i--;
        }
    }

    private void setupStopWatchView() {

        basedJFrame.getContentPane().removeAll();
        basedJFrame.repaint();

        basedJFrame.add(stopWatchView);
        basedJFrame.setVisible(true);
        basedJFrame.pack();
    }

    public void operate() {

        isPause = true;

        JButton startButton = stopWatchView.getStartButton();
        JButton pauseButton = stopWatchView.getPauseButton();
        JButton clockModeButton = stopWatchView.getClockModeButton();

        startButton.addActionListener(e -> {

            if (currentDuration != null && !currentDuration.equals(Duration.ZERO)) {
                savedLabels.add(getTimeModel.durationToString(currentDuration));
                savedLabels.pollFirst();
                populateSavedLabels();
            }

            isPause = false;
            operationCount++;
            currentDuration = Duration.ZERO;

            Thread countThread = new Thread(new StartCountingTime());
            Thread countMilliSecThread = new Thread(new StartCountingMilliSec());
            countThread.start();
            countMilliSecThread.start();

        });

        pauseButton.addActionListener(e -> {

            if (!isPause) {
                isPause = true;
                return;
            }

            if (operationCount == 0) {
                return;
            }

            isPause = false;
            operationCount++;

            Thread countThread = new Thread(new StartCountingTime());
            Thread countMilliSecThread = new Thread(new StartCountingMilliSec());
            countThread.start();
            countMilliSecThread.start();
        });

        clockModeButton.addActionListener(e -> {

            operationCount++;
            new ClockController(basedJFrame, alarmSet);
        });
    }



    private class StartCountingMilliSec implements Runnable {
        @Override
        public void run() {

            int currentOperation = operationCount;

            LocalDateTime atStartPoint = LocalDateTime.now();
            atStartPoint = atStartPoint.minus(currentDuration);
            JLabel mSec = stopWatchView.getMilliSecLabel();

            while (!isPause && currentOperation == operationCount) {

                currentDuration = Duration.between(atStartPoint, LocalDateTime.now());
                long millis = currentDuration.toMillis();
                mSec.setText(String.format("%02d", (millis / 10) % 100));

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private class StartCountingTime implements Runnable {
        @Override
        public void run() {

            int currentOperation = operationCount;
            LocalDateTime atStartPoint = LocalDateTime.now();
            atStartPoint = atStartPoint.minus(currentDuration);

            JLabel hourLabel = stopWatchView.getHourLabel();
            JLabel minLabel = stopWatchView.getMinLabel();
            JLabel secLabel = stopWatchView.getSecLabel();

            while (!isPause && currentOperation == operationCount) {

                currentDuration = Duration.between(atStartPoint, LocalDateTime.now());
                long second = currentDuration.getSeconds();
                secLabel.setText(String.format("%02d", second % 60));
                minLabel.setText(String.format("%02d", second / 60 % 60));
                hourLabel.setText(String.format("%02d", second / 60 / 60 % 60));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
