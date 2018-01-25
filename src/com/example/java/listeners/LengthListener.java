package com.example.java.listeners;

import com.example.java.frames.MainFrame;

import java.util.concurrent.TimeUnit;

public class LengthListener implements Runnable {

    private MainFrame mainFrame;

    public LengthListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void run() {

        while(true) {
            int length = mainFrame.getUserMessage().getText().length();
            int left = 135 - length;
            String leftChars = String.valueOf(left);
            if (length > 135) {
                setButtons(false);
                mainFrame.getWarning().setText("Chars left: 0");
            } else if (length < 135) {
                setButtons(true);
                mainFrame.getWarning().setText("Chars left: " + leftChars);
            }
            sleep();
        }
    }


    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
            //or Thread.sleep(1000)
        }
        catch (InterruptedException e) {
            //nothing
        }
    }

    private void setButtons(boolean bool) {
        mainFrame.getLmt().setEnabled(bool);
        mainFrame.getBite().setEnabled(bool);
    }
}


