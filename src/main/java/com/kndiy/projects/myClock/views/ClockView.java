package com.kndiy.projects.myClock.views;

import com.kndiy.projects.myClock.models.FontModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class ClockView extends JPanel {


    private JButton stopWatchModeButton;
    private JButton addAlarmButton;
    private JLabel hourLabel;
    private JLabel minuteLabel;
    private JLabel secondLabel;
    private final JLabel weekdayLabel;
    private final JLabel dateLabel;
    private Font font;
    private final int DEFAULT_WIDTH;
    private ArrayList<JLabel> alarmList;

    public ClockView(int defaultWidth) {

        this.DEFAULT_WIDTH = defaultWidth;
        font = new FontModel().createFontFromFile();

        this.setLayout(new GridBagLayout());

        weekdayLabel = new JLabel("", SwingConstants.CENTER);
        dateLabel = new JLabel("", SwingConstants.CENTER);
        setupSmallLabel(weekdayLabel);
        setupSmallLabel(dateLabel);

        setupTheRest();
    }

    private void setupTheRest() {
        JPanel timePanel = constructTimePanel();

        JPanel weekdayPanel = new JPanel(new FlowLayout());
        weekdayPanel.add(weekdayLabel);

        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.add(dateLabel);

        GridBagConstraints constraints;

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(timePanel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(weekdayPanel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        this.add(datePanel, constraints);

        JPanel buttonPanel = constructButtonPanel();

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        this.add(buttonPanel, constraints);
    }

    private JPanel constructButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setPreferredSize(new Dimension(DEFAULT_WIDTH, 100));

        Dimension buttonDimension = new Dimension(150,60);

        JPanel alarmPanel = new JPanel();
        alarmPanel.setPreferredSize(new Dimension(150,80));
        alarmList = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            JLabel alarmLabel = new JLabel("__:__:__", SwingConstants.CENTER);
            alarmLabel.setPreferredSize(new Dimension(140,20));
            alarmLabel.setFont(font.deriveFont(20f));
            alarmPanel.add(alarmLabel);
            alarmList.add(alarmLabel);
        }
        buttonPanel.add(alarmPanel);

        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(10,20));
        buttonPanel.add(separator);

        addAlarmButton = new JButton("Add New Alarm");
        addAlarmButton.setPreferredSize(buttonDimension);
        buttonPanel.add(addAlarmButton);

        separator = new JPanel();
        separator.setPreferredSize(new Dimension(10,20));
        buttonPanel.add(separator);

        stopWatchModeButton = new JButton("Stop Watch Mode");
        stopWatchModeButton.setPreferredSize(buttonDimension);
        buttonPanel.add(stopWatchModeButton);

        return buttonPanel;
    }

    private void setupSmallLabel(JLabel label) {
        label.setPreferredSize(new Dimension(DEFAULT_WIDTH, 60));
        label.setFont(font.deriveFont(50f));
    }

    private JPanel constructTimePanel() {

        Dimension timeCellSize = new Dimension(110,100);

        hourLabel = new JLabel("", SwingConstants.CENTER);
        hourLabel.setFont(font.deriveFont(75f));
        hourLabel.setForeground(Color.WHITE);
        hourLabel.setPreferredSize(timeCellSize);

        minuteLabel = new JLabel("", SwingConstants.CENTER);
        minuteLabel.setFont(font.deriveFont(75f));
        minuteLabel.setForeground(Color.WHITE);
        minuteLabel.setPreferredSize(timeCellSize);

        secondLabel = new JLabel("", SwingConstants.CENTER);
        secondLabel.setFont(font.deriveFont(75f));
        secondLabel.setForeground(Color.WHITE);
        secondLabel.setPreferredSize(timeCellSize);

        JLabel separator;

        JPanel timePanel = new JPanel(new GridBagLayout());
        timePanel.setPreferredSize(new Dimension(DEFAULT_WIDTH,100));
        timePanel.setBackground(Color.BLACK);
        timePanel.setOpaque(true);

        GridBagConstraints gridBagConstraints;

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        timePanel.add(hourLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        separator = makeSeparatorLabel();
        timePanel.add(separator, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        timePanel.add(minuteLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        separator = makeSeparatorLabel();
        timePanel.add(separator, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        timePanel.add(secondLabel, gridBagConstraints);

        return timePanel;
    }

    private JLabel makeSeparatorLabel() {
        JLabel separator = new JLabel();
        separator.setFont(font.deriveFont(75f));
        separator.setForeground(Color.WHITE);
        separator.setText(":");

        return separator;
    }

    public JLabel getHourLabel() {
        return hourLabel;
    }

    public JLabel getMinuteLabel() {
        return minuteLabel;
    }

    public JLabel getSecondLabel() {
        return secondLabel;
    }

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
    }

    public JLabel getWeekdayLabel() {
        return weekdayLabel;
    }

    public JLabel getDateLabel() {
        return dateLabel;
    }

    public JButton getStopWatchModeButton() {
        return stopWatchModeButton;
    }

    public JButton getAddAlarmButton() {
        return addAlarmButton;
    }

    public ArrayList<JLabel> getAlarmList() {
        return alarmList;
    }
}
