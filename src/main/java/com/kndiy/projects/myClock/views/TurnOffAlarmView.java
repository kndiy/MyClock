package com.kndiy.projects.myClock.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class TurnOffAlarmView extends JDialog {

    private JButton turnOffButton;
    private final int WIDTH = 350;
    public TurnOffAlarmView() {
        this.setLayout(new FlowLayout());
        this.setBounds(50,50,WIDTH,250);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setupTheRest();
        addListenerToButton();

        this.setModal(true);
        this.setVisible(true);
        this.pack();
    }

    private void setupTheRest() {

        this.add(makeSeparator());

        turnOffButton = new JButton("<html>Turn Off<br/>Alarm<html/>");
        turnOffButton.setPreferredSize(new Dimension(150,50));
        this.add(turnOffButton);

        this.add(makeSeparator());
    }

    private JLabel makeSeparator() {
        JLabel separator = new JLabel();
        separator.setPreferredSize(new Dimension(WIDTH, 50));
        return separator;
    }

    private void addListenerToButton() {
        turnOffButton.addActionListener(e -> {
            this.setVisible(false);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
    }
}
