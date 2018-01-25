package com.example.java.frames;

import com.example.java.listeners.ButtonListener;
import com.example.java.listeners.LengthListener;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame {

    private JLabel chooseOperator, warning;
    private JTextArea userMessage;
    private JTextField phoneNumber;
    private JPanel mainPanel;
    private JButton lmt, bite;

    public MainFrame () {
        this.setTitle("SMS Sender");
        Dimension dimension = new Dimension();
        dimension.setSize(400, 270);
        this.setMaximumSize(dimension);
        this.setMinimumSize(dimension);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        mainPanel = new JPanel();
        chooseOperator = new JLabel("Choose operator");
        warning = new JLabel("");
        phoneNumber = new JTextField("Enter phone number");


        userMessage = new JTextArea("Enter your message...");
        userMessage.setRows(7);
        userMessage.setColumns(30);
        userMessage.setLineWrap(true);


        lmt = new JButton("LMT");
        bite = new JButton("Bite");

        bite.addActionListener(new ButtonListener(this));
        lmt.addActionListener(new ButtonListener(this));

        Box phoneNumberBox = Box.createVerticalBox();

        phoneNumberBox.add(phoneNumber);
        phoneNumberBox.add(userMessage);

        Box operatorBox = Box.createHorizontalBox();
        operatorBox.add(chooseOperator);
        operatorBox.add(lmt);
        operatorBox.add(bite);

        mainPanel.add(phoneNumberBox, BorderLayout.NORTH);
        mainPanel.add(operatorBox, BorderLayout.CENTER);
        mainPanel.add(warning, BorderLayout.SOUTH);

        this.add(mainPanel);
        this.setVisible(true);

        LengthListener lengthListener = new LengthListener(this);
        lengthListener.run();

    }

    public JTextArea getUserMessage() {
        return userMessage;
    }

    public JTextField getPhoneNumber() {
        return phoneNumber;
    }


    public JButton getLmt() {
        return lmt;
    }

    public JButton getBite() {
        return bite;
    }

    public JLabel getWarning() {
        return warning;
    }

    public void setUserMessage() {
        this.userMessage.setText("Enter your message...");
    }

    public void setPhoneNumber() {
        this.phoneNumber.setText("Enter phone number");
    }
}
