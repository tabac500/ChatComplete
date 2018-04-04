/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomplete.userview;
import chatcomplete.ConnectDatabase;
import chatcomplete.UserPacket;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*; 
import java.util.*; 
/**
 *
 * @author taba
 */
public class ToolFrame extends JFrame {
    public ToolFrame() {
        this.setSize(400, 500);
        this.setLayout(new GridLayout(2, 1));
        JPanel firstPanel = new JPanel(); 
        firstPanel.setLayout(new GridLayout(4, 1));
        JTextArea resultArea = new JTextArea(); 
        JScrollPane secondPanel = new JScrollPane(resultArea); 
        
        JPanel getPortOfUserPanel = new JPanel(); 
        getPortOfUserPanel.setLayout(new GridLayout(1, 3)); 
        getPortOfUserPanel.add(new JLabel("What is port of")); 
        JTextField getPortOfUserTextField = new JTextField(); 
        getPortOfUserPanel.add(getPortOfUserTextField); 
        JButton getPortOfUserButton = new JButton("Yup"); 
        getPortOfUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultArea.setText(getPortOfUserTextField.getText() + " is on port: ");
                Iterator<UserPacket> i = ConnectDatabase.getPortOfUser(getPortOfUserTextField.getText()).iterator();
                while (i.hasNext()) {
                    UserPacket userPacket = i.next(); 
                    resultArea.append("" + userPacket.getStatus());
                }
            }
        });
        getPortOfUserPanel.add(getPortOfUserButton); 
        firstPanel.add(getPortOfUserPanel); 
        
        JPanel getUserOnPortPanel = new JPanel(); 
        getUserOnPortPanel.setLayout(new GridLayout(1, 3));
        getUserOnPortPanel.add(new JLabel("Who are on port")); 
        JTextField getUserOnPortTextField = new JTextField(); 
        getUserOnPortPanel.add(getUserOnPortTextField); 
        JButton getUserOnPortButton = new JButton("Yup"); 
        getUserOnPortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("List of user who is on port " + getUserOnPortTextField.getText() + " :\n");
                Iterator<UserPacket> i = ConnectDatabase.getUsersonPort(getUserOnPortTextField.getText()).iterator(); 
                while (i.hasNext()) {
                    UserPacket userPacket = i.next(); 
                    resultArea.append(userPacket.getUsername() + "\n");
                }
            }
        });
        getUserOnPortPanel.add(getUserOnPortButton); 
        firstPanel.add(getUserOnPortPanel); 
        
        JPanel getUsersPanel = new JPanel(); 
        getUsersPanel.setLayout(new GridLayout(1, 3));
        getUsersPanel.add(new JLabel("Get all users"));
        getUsersPanel.add(new JPanel()); 
        JButton getUsersButton = new JButton("Yup"); 
        getUsersPanel.add(getUsersButton); 
        getUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("List of users: \n");
                Iterator<UserPacket> i = ConnectDatabase.getUsers().iterator();
                while (i.hasNext()) {
                    UserPacket userPacket = i.next(); 
                    resultArea.append(userPacket.getUsername() + "\n");
                }
            }
        });
        firstPanel.add(getUsersPanel); 
        
        
        
        JPanel getUserIsActivePanel = new JPanel(); 
        getUserIsActivePanel.setLayout(new GridLayout(1, 3));
        getUserIsActivePanel.add(new JLabel("Who is active")); 
        getUserIsActivePanel.add(new JLabel()); 
        JButton getUserIsActiveButton = new JButton("Yup");
        getUserIsActiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("List of users who is on port: \n");
                Iterator<UserPacket> i = ConnectDatabase.getUsersIsActive().iterator(); 
                while (i.hasNext()) {
                    UserPacket userPacket = i.next(); 
                    resultArea.append(userPacket.getUsername() + " " + userPacket.getStatus() + "\n");
                }
            }
        });
        getUserIsActivePanel.add(getUserIsActiveButton); 
        firstPanel.add(getUserIsActivePanel); 
               
        this.add(firstPanel); 
        this.add(secondPanel); 
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //this.setVisible(true);
    }
    
    public static void main(String[] args) {
        new ToolFrame(); 
    }

   
}
