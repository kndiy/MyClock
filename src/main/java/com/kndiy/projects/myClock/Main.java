package com.kndiy.projects.myClock;

import com.kndiy.projects.myClock.controllers.ClockController;
import com.kndiy.projects.myClock.views.BasedJFrame;

public class Main {
    public static void main(String[] args) {

        BasedJFrame basedJFrame = new BasedJFrame(500);
        new ClockController(basedJFrame);


    }
}