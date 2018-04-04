/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomplete.loginview;
import chatcomplete.ConnectDatabase;
import chatcomplete.userview.GUIClientWelcome;
import chatcomplete.loginview.LoginPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*; 
/**
 *
 * @author taba
 */
public class SubmitPanel extends JPanel { 
    private String username, password; 

    public String getUsername() {
        return username;
    }
    
    
    public SubmitPanel(LoginPanel jPanel2) {
        this.setLayout(new GridLayout(3, 1));
        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new GridLayout(1, 2));
        firstPanel.add(new JPanel()); 
        JButton submitButton = new JButton("Login"); 
        //submitButton.addActionListener();
        firstPanel.add(submitButton);
        this.add(firstPanel); 
        JLabel secondPanel = new JLabel("", JLabel.CENTER); 
        secondPanel.setText("");
        this.add(secondPanel); 
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = jPanel2.getUsernameArea().getText(); 
                password = String.valueOf(jPanel2.getPasswordArea().getPassword()); 
                int re = ConnectDatabase.VerifyUser(username, password);
                switch (re) {
                    case -1: secondPanel.setText("Invalid username or password");
                             secondPanel.setForeground(Color.red);
                             break; 
                    case 0:  ConnectDatabase.updateStatus(username, -2);
                             String[] args = {username}; 
                             GUIClientWelcome.main(args);
                             //new GUIClientWelcome(username);
                             break;
                    case -2: secondPanel.setText(username + "is active, but in no room");
                             secondPanel.setForeground(Color.MAGENTA);
                             break; 
                    default: secondPanel.setText(username +" is on port " + re);
                             secondPanel.setForeground(Color.green);
                             break; 
                }   
            }
        });
        this.add(new JPanel()); 
        //this.add(new JPanel()); 
    }
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame(); 
        mainFrame.add(new SubmitPanel(new LoginPanel())); 
        mainFrame.setSize(300, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
