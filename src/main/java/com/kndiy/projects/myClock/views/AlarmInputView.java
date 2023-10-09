package com.kndiy.projects.myClock.views;

import com.kndiy.projects.myClock.controllers.AlarmInputController;
import com.kndiy.projects.myClock.models.FontModel;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AlarmInputView extends JDialog {

    private JButton okButton;
    private JButton cancelButton;
    private final AlarmInputController alarmInputController;
    private final Font font;
    private final ArrayList<JTextField> inputArray;
    private final int WIDTH = 350;
    public AlarmInputView(AlarmInputController alarmInputController) {

        this.setLayout(new FlowLayout());
        this.setBounds(50,50,WIDTH,250);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        font = new FontModel().createFontFromFile();
        this.inputArray = new ArrayList<>();
        this.alarmInputController = alarmInputController;

        setupTheRest();
        addListenerToButton();

        this.setModal(true);
        this.setVisible(true);
        this.pack();
    }

    private void setupTheRest() {
        JLabel promptLabel = new JLabel("Please input new Alarm with 24h format", SwingConstants.CENTER);
        promptLabel.setPreferredSize(new Dimension(WIDTH,20));
        this.add(promptLabel);

        JPanel alarmInputPanel = constructAlarmInputPanel();
        this.add(alarmInputPanel);

        Dimension buttonDimension = new Dimension(100,40);

        okButton = new JButton("OK");
        okButton.setPreferredSize(buttonDimension);
        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(buttonDimension);

        JLabel separator = new JLabel();
        separator.setPreferredSize(new Dimension(30,40));

        this.add(okButton);
        this.add(separator);
        this.add(cancelButton);
    }

    private JPanel constructAlarmInputPanel() {

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setPreferredSize(new Dimension(WIDTH, 100));
        GridBagConstraints constraints;
        Dimension inputDimension = new Dimension(80,60);

        for (int i = 0; i < 5; i += 2) {

            JTextField input = new JTextField();
            input.setFont(font.deriveFont(50f));
            input.setPreferredSize(inputDimension);
            input.setMinimumSize(inputDimension);

            constraints = new GridBagConstraints();
            constraints.gridx = i;
            constraints.gridy = 0;
            inputPanel.add(input, constraints);
            AbstractDocument abstractDocument = (AbstractDocument) input.getDocument();
            abstractDocument.setDocumentFilter(new AlarmInputDocumentFilter(2));
            inputArray.add(input);

            if (i < 4) {
                constraints = new GridBagConstraints();
                constraints.gridx = i + 1;
                constraints.gridy = 0;
                inputPanel.add(createSeparator(), constraints);
            }
        }

        for (int i = 0; i < 3; i ++) {
            JTextField textField = inputArray.get(i);
            textField.addKeyListener(new AlarmInputKeyListener(textField));
        }

        return inputPanel;
    }

    private void addListenerToButton() {
        okButton.addActionListener(e -> {
            alarmInputController.setAlarmInput(inputArrayToString());
            this.setVisible(false);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });

        cancelButton.addActionListener(e -> {
            this.setVisible(false);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
    }

    private String inputArrayToString() {
        StringBuilder builder = new StringBuilder();

        for (JTextField textField : inputArray) {
            builder.append(textField.getText());
            builder.append(":");
        }

        return builder.toString();
    }

    private JLabel createSeparator() {
        JLabel separator = new JLabel(":");
        separator.setFont(font.deriveFont(50f));
        return separator;
    }

    private record AlarmInputKeyListener(JTextField textField) implements KeyListener {

        @Override
            public void keyTyped(KeyEvent e) {

                if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                    try {
                        Integer.parseInt(e.getKeyChar() + "");
                    } catch (NumberFormatException ex) {
                        e.setKeyChar('\0');
                        return;
                    }
                }

                if (textField.getText().length() == 2) {
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                }
            }

        @Override
            public void keyPressed(KeyEvent e) {
            }

        @Override
            public void keyReleased(KeyEvent e) {
            }
        }
    private static class AlarmInputDocumentFilter extends DocumentFilter {

        private final int limit;
        public AlarmInputDocumentFilter(int limit) {
            super();
            this.limit = limit;
        }

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {

            if (fb.getDocument().getLength() + string.length() > limit) {
                return;
            }
            fb.insertString(offset, string, attr);
        }

        @Override
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {

            fb.remove(offset, length);
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

            if(fb.getDocument().getLength() + text.length() > limit) {
                return;
            }
            fb.insertString(offset, text, attrs);
        }
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public ArrayList<JTextField> getInputArray() {
        return inputArray;
    }
}
