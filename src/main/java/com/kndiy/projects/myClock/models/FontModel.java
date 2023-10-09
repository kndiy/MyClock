package com.kndiy.projects.myClock.models;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontModel {

    public Font createFontFromFile() {
        try {
            return Font.createFont(Font.PLAIN, getFontInputStream());
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    private InputStream getFontInputStream() {
        return this.getClass().getResourceAsStream("/fonts/MyFont.ttf");
    }
}
