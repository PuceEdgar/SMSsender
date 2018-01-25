package com.example.java.listeners;

import com.example.java.frames.MainFrame;
import com.example.java.messageSenders.SendMessageToBite;
import com.example.java.messageSenders.SendMessageToLMT;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ButtonListener implements ActionListener {

    private MainFrame mainFrame;

    public ButtonListener (MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFrame.getBite()) {
            try {
                SendMessageToBite sendMessageToBite = new SendMessageToBite(mainFrame);
            }
            catch (IOException ev) {
                ev.printStackTrace();
            }
        }


        if (e.getSource() == mainFrame.getLmt()) {
            try {
                SendMessageToLMT sendMessageToLMT = new SendMessageToLMT(mainFrame);
            }
            catch (IOException ev) {
                ev.printStackTrace();
            }
        }
    }
}
