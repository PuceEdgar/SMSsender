package com.example.java.messageSenders;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import com.example.java.frames.MainFrame;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.gargoylesoftware.htmlunit.WebClient;

public class SendMessageToLMT {

    private JFrame editorFrame;
    private MainFrame mainFrame;
    private JLabel captchaImg;
    private JTextField captcha;
    private HtmlPage page1 = new WebClient().getPage("https://www.lmt.lv/lv/?sms");

    public SendMessageToLMT(MainFrame mainFrame) throws IOException{
        this.mainFrame = mainFrame;

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                editorFrame = new JFrame("CAPTCHA");
                editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                BufferedImage image = null;

                try
                {

                    URL imageUrl = new URL(urlLink());
                    InputStream in = imageUrl.openStream();
                    image = ImageIO.read(in);
                    in.close();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    System.exit(1);
                }
                ImageIcon imageIcon = new ImageIcon(image);
                captchaImg = new JLabel(imageIcon);
                captcha = new JTextField();
                JButton sendSMS = new JButton("send");
                sendSMS.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (e.getSource() == sendSMS) {
                                    send();
                                }
                            }
                        }
                );
                captchaImg.setIcon(imageIcon);

                editorFrame.getContentPane().add(captchaImg, BorderLayout.NORTH);
                editorFrame.getContentPane().add(captcha, BorderLayout.CENTER);
                editorFrame.getContentPane().add(sendSMS, BorderLayout.SOUTH);

                editorFrame.pack();

                editorFrame.setLocationRelativeTo(null);
                editorFrame.setVisible(true);
            }
        });
    }

    private String urlLink() {
            HtmlImage img = (HtmlImage) page1.getElementById("login-code-img");

            return img.getSrcAttribute();
    }



    private void send(){
       try  {

           HtmlButton send = (HtmlButton) page1.getElementById("sms-form-submit");
           HtmlTextInput phoneNumber = (HtmlTextInput) page1.getElementById("sms-number");
           HtmlTextInput captchaInput = (HtmlTextInput) page1.getElementById("sms-code");
           HtmlTextArea smsText = (HtmlTextArea) page1.getElementById("sms-text");

           // Change the value of the text field

           phoneNumber.setValueAttribute(mainFrame.getPhoneNumber().getText());
           captchaInput.setValueAttribute(captcha.getText());
           smsText.setTextContent(mainFrame.getUserMessage().getText());

           send.click();
           editorFrame.setVisible(false);
           mainFrame.setPhoneNumber();
           mainFrame.setUserMessage();
       }
       catch (IOException e) {
           e.getStackTrace();
       }
    }



}
