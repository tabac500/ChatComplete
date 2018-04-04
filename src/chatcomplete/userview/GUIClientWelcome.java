/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomplete.userview;
import java.awt.*;
import java.awt.event.MouseAdapter;
import javax.swing.*; 
import java.awt.event.*;
import chatcomplete.loginview.*; 
import chatcomplete.ConnectDatabase;
import chatcomplete.ClientGUI;
import chatcomplete.CheckPort; 
/**
 *
 * @author taba
 */
public class GUIClientWelcome extends JFrame {
    public static ChatComplete a; 
    public GUIClientWelcome(String username) {
        ToolFrame toolFrame = new ToolFrame(); 
        AdminToolFrame adminToolFrame = new AdminToolFrame(); 
        this.setSize(500, 300);
        this.setLayout(new GridLayout(5, 1));
        JLabel label1 = new JLabel("Welcome " + username + ", you are on no port");
        label1.setForeground(Color.green);
        this.add(label1);
        
        JLabel label2 = new JLabel("Enter the port you wanna join below"); 
        this.add(label2); 
        
        JPanel portPanel = new JPanel(); 
        portPanel.setLayout(new GridLayout(1, 4));
        JTextField portField = new JTextField(); 
        portPanel.add(portField); 
        portPanel.add(new JPanel());
        portPanel.add(new JPanel());
        portPanel.add(new JPanel()); 
        this.add(portPanel); 
        
        JLabel alertLabel = new JLabel(""); 
        alertLabel.setForeground(Color.red);
        this.add(alertLabel); 
        portField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CheckPort.isPortAvailable(Integer.parseInt(portField.getText())))
                {
                    ConnectDatabase.updateStatus(username, Integer.parseInt(portField.getText()));
                    String[] args = {username, portField.getText()}; 
                    ClientGUI.main(args);
                    //new ClientGUI(username, Integer.parseInt(portField.getText())); 
                }         
                else alertLabel.setText("Incorrect Port");
            }
        });
       
        
        JPanel toolPanel = new JPanel(); 
        toolPanel.setLayout(new GridLayout(1, 3));
        JButton logoutButton = new JButton("Logout"); 
        toolPanel.add(logoutButton); 
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectDatabase.updateStatus(username, 0);
                ChatComplete.main(null);
                CloseThisFrame(); 
            }
        });
        
        JButton adminToolButton = new JButton("Admin Tools"); 
        adminToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (adminToolFrame.isVisible()) {
                    adminToolFrame.setVisible(false);
                } else 
                    adminToolFrame.setVisible(true);
            }
        });
        toolPanel.add(adminToolButton);
        if (username.equalsIgnoreCase("taba")) adminToolButton.setVisible(true);
        else adminToolButton.setVisible(false);
        JLabel toolLink = new JLabel("Show Advanced"); 
        toolLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (toolFrame.isVisible()) {
                    toolFrame.setVisible(true); 
                    toolLink.setText("Show Advanced");
                    toolFrame.setVisible(false);
                }
                else {
                    toolLink.setText("Hide Advanced");
                    toolFrame.setVisible(true);
                }
            }
        });
        toolPanel.add(toolLink); 
        this.add(toolPanel); 
        //this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                ConnectDatabase.updateStatus(username, 0);
                toolFrame.dispose();
                adminToolFrame.dispose();
                CloseThisFrame();
            }
        });
        this.setResizable(false);
        this.setVisible(true);
        a.dispose();
    }
    
    void CloseThisFrame() {
        super.dispose();
    }
    
    public static void main(String[] args) {
        GUIClientWelcome guiClientWelcome = new GUIClientWelcome(args[0]); 
        ClientGUI.x = guiClientWelcome; 
        //new GUIClientWelcome(args[0]); 
    }
}
