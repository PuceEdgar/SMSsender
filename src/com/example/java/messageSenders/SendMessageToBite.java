package com.example.java.messageSenders;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import com.example.java.frames.MainFrame;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;

public class SendMessageToBite {

    private MainFrame mainFrame;
    private JLabel captchaImg;
    private JTextField captcha;
    private JFrame editorFrame;

    public SendMessageToBite(MainFrame mainFrame) throws IOException{
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

                    URL imageUrl = new URL(imgURL().toString());
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

    private StringBuilder imgURL() throws IOException{
        Document doc = Jsoup.connect("https://www.bite.lv/lv/sms").get();
        Elements img = doc.select("img[src]");
        StringBuilder url = new StringBuilder();
        url.append("https://www.bite.lv");

        for (Element image: img) {
            if (image.attr("title").contains("CAPTCHA")) {
                url.append(image.attr("src"));
            }
        }
        System.out.println(url);
        return  url;
    }



    private void send(){
        try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            // Get the first page
            HtmlPage page1 = webClient.getPage("https://www.bite.lv/lv/sms");

            // Get the form that we are dealing with and within that form,
            // find the submit button and the field that we want to change.
            HtmlForm form = page1.getFormByName("");
            List<DomElement> buttons = page1.getElementsById("edit-submit--2");
            DomElement send = buttons.get(1);
            HtmlTextInput phoneNumber = (HtmlTextInput) page1.getElementById("edit-sms-msisdn");
            HtmlTextInput captchaInput = (HtmlTextInput) page1.getElementById("edit-captcha-response");
            HtmlTextArea smsText = (HtmlTextArea) page1.getElementById("edit-sms-body");

            // Change the value of the text field

            phoneNumber.setValueAttribute(mainFrame.getPhoneNumber().getText());
            captchaInput.setValueAttribute(captcha.getText());
            smsText.setTextContent(mainFrame.getUserMessage().getText());

            send.click();
            editorFrame.setVisible(false);
            mainFrame.setPhoneNumber();
            mainFrame.setUserMessage();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
