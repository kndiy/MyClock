package com.kndiy.projects.myClock.views;

import javax.swing.*;
import java.awt.*;

public class BasedJFrame extends JFrame {

    private final int DEFAULT_WIDTH;
    public BasedJFrame(int defaultWidth) {

        this.DEFAULT_WIDTH = defaultWidth;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setTitle("My Clock");
        this.setSize(DEFAULT_WIDTH,450);
        this.setResizable(false);
    }

    public int getDEFAULT_WIDTH() {
        return DEFAULT_WIDTH;
    }
}
