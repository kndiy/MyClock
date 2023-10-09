package com.kndiy.projects.myClock.views;

import com.kndiy.projects.myClock.models.FontModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StopWatchView extends JPanel {

    private final Font font;
    private final int DEFAULT_WIDTH;
    private JLabel hourLabel;
    private JLabel minLabel;
    private JLabel secLabel;
    private JLabel milliSecLabel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton clockModeButton;
    private ArrayList<JLabel> savedLabels;

    public StopWatchView(int defaultWidth) {

        DEFAULT_WIDTH = defaultWidth;
        font = new FontModel().createFontFromFile();
        this.setLayout(new GridBagLayout());

        setupTimeLabel();
        setupTheRest();
    }

    private void setupTimeLabel() {

        Dimension timeCellSize = new Dimension(100,100);
        float fontSize = 75f;

        hourLabel = new JLabel("00", SwingConstants.CENTER);
        hourLabel.setFont(font.deriveFont(fontSize));
        hourLabel.setForeground(Color.WHITE);
        hourLabel.setMinimumSize(timeCellSize);
        hourLabel.setPreferredSize(timeCellSize);

        minLabel = new JLabel("00", SwingConstants.CENTER);
        minLabel.setFont(font.deriveFont(fontSize));
        minLabel.setForeground(Color.WHITE);
        minLabel.setMinimumSize(timeCellSize);
        minLabel.setPreferredSize(timeCellSize);

        secLabel = new JLabel("00", SwingConstants.CENTER);
        secLabel.setFont(font.deriveFont(fontSize));
        secLabel.setForeground(Color.WHITE);
        secLabel.setMinimumSize(timeCellSize);
        secLabel.setPreferredSize(timeCellSize);

        milliSecLabel = new JLabel("00", SwingConstants.CENTER);
        milliSecLabel.setFont(font.deriveFont(fontSize));
        milliSecLabel.setForeground(Color.WHITE);
        milliSecLabel.setMinimumSize(timeCellSize);
        milliSecLabel.setPreferredSize(timeCellSize);
    }


    private void setupTheRest() {

        JPanel timePanel = constructTimePanel();
        timePanel.setOpaque(true);
        timePanel.setBackground(Color.BLACK);

        JPanel buttonPanel = constructButtonPanel();

        GridBagConstraints constraints;

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(timePanel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(buttonPanel, constraints);

    }

    private JPanel constructButtonPanel() {

        Dimension buttonSize = new Dimension(75,50);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints;

        startButton = new JButton("Start");
        startButton.setPreferredSize(buttonSize);

        pauseButton = new JButton("Pause");
        pauseButton.setPreferredSize(buttonSize);

        clockModeButton = new JButton("<html>Clock<br/>Mode<html/>");
        clockModeButton.setPreferredSize(buttonSize);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        constraints.insets = new Insets(20,30,20,0);
        buttonPanel.add(startButton, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        constraints.insets = new Insets(20,30,20,0);
        buttonPanel.add(pauseButton, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        constraints.insets = new Insets(20,30,20,0);
        buttonPanel.add(clockModeButton, constraints);

        savedLabels = new ArrayList<>(3);
        for (int i = 0; i < 3; i ++) {
            JLabel savedLabel = new JLabel("", SwingConstants.LEFT);
            savedLabel.setFont(font.deriveFont(20f));
            constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = i;
            constraints.insets = new Insets(10,0,0,0);
            savedLabels.add(savedLabel);
            buttonPanel.add(savedLabel, constraints);
        }

        return buttonPanel;
    }

    private JPanel constructTimePanel() {

        JPanel timePanel = new JPanel(new GridBagLayout());
        timePanel.setPreferredSize(new Dimension(DEFAULT_WIDTH, 150));

        GridBagConstraints constraints;

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        constraints.insets = new Insets(0,10,0,10);
        timePanel.add(hourLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 5;
        constraints.gridy = 0;
        timePanel.add(makeSeparatorLabel(), constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 6;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        constraints.insets = new Insets(0,10,0,10);
        timePanel.add(minLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 11;
        constraints.gridy = 0;
        timePanel.add(makeSeparatorLabel(), constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 12;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        constraints.insets = new Insets(0,10,0,10);
        timePanel.add(secLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 17;
        constraints.gridy = 0;
        timePanel.add(makeSeparatorLabel(), constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 18;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        constraints.insets = new Insets(0,10,0,10);
        timePanel.add(milliSecLabel, constraints);


        //name labels
        //name labels
        //name labels
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 5;
        timePanel.add(makeNameLabel("hour"), constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 6;
        constraints.gridy = 1;
        constraints.gridwidth = 5;
        timePanel.add(makeNameLabel("minute"), constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 12;
        constraints.gridy = 1;
        constraints.gridwidth = 5;
        timePanel.add(makeNameLabel("second"), constraints);

        return timePanel;
    }

       private JLabel makeNameLabel(String name) {
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(font.deriveFont(18f));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setPreferredSize(new Dimension(100,25));

        return nameLabel;
    }
    private JLabel makeSeparatorLabel() {
        JLabel separator = new JLabel(":");
        separator.setFont(font.deriveFont(75f));
        separator.setForeground(Color.WHITE);
        separator.setPreferredSize(new Dimension(30,125));

        return separator;
    }

    public JLabel getHourLabel() {
        return hourLabel;
    }

    public JLabel getMinLabel() {
        return minLabel;
    }

    public JLabel getSecLabel() {
        return secLabel;
    }

    public JLabel getMilliSecLabel() {
        return milliSecLabel;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getPauseButton() {
        return pauseButton;
    }

    public JButton getClockModeButton() {
        return clockModeButton;
    }

    public ArrayList<JLabel> getSavedLabels() {
        return savedLabels;
    }
}
