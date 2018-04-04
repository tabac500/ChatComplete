/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomplete;
import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import sun.management.jdp.JdpController;
import sun.management.jdp.JdpJmxPacket;
/**
 *
 * @author taba
 */
public class ServerGUI extends JFrame {
    JPanel firstPanel;
    ServerSocket serverSocket; 
    JTextField inputPort; 
    JButton switchButton; 
    JLabel informLabel; 
    boolean isRunning = false; 
    ArrayList<ServerRunning.ClientThread> ar = new ArrayList<ServerRunning.ClientThread>(); 
    public ServerGUI() {
        this.setSize(300, 100);
        this.setLayout(new GridLayout(2, 1));
        firstPanel = new JPanel(); 
        firstPanel.setLayout(new GridLayout(1, 2));
        inputPort = new JTextField("1234");
        firstPanel.add(inputPort); 
        switchButton = new JButton("Start"); 
        switchButton.addActionListener(new HandlerButton());
        firstPanel.add(switchButton); 
        this.add(firstPanel);
        informLabel = new JLabel(); 
        this.add(informLabel); 
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (serverSocket != null) try {
                    serverSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                stop();
            }
        });
    }
    
    void removeClient(String username) {
        for (int i = 0; i < ar.size(); i++) {
            ServerRunning.ClientThread ct = ar.get(i);
            if (ct.username.equalsIgnoreCase(username))
            {
                ar.remove(i); 
                ct.disconnect(); 
            }
        }
    }
    
    void stop() {
        for (int i = 0; i < ar.size(); i++) {
            ServerRunning.ClientThread ct = ar.get(i);
            ConnectDatabase.updateStatus(ct.username, 0);
            ct.disconnect(); 
        }
    }
    
    public static void main(String[] args) {
        new ServerGUI();
    }
    
    public void broadcast(ChatMessage message) {
        for (int i = 0; i < ar.size(); i++) {
            ServerRunning.ClientThread ct = ar.get(i);
            ct.sendMessage(message); 
        }
    }
    
    class HandlerButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isRunning) {
                try { 
                    //System.out.println("OK di");
                    serverSocket = new ServerSocket(Integer.parseInt(inputPort.getText()));
                    new ServerRunning().start(); 
                    isRunning = true;
                } catch (Exception ex) {
                    informLabel.setText("Incorrect port. Please try again");
                    informLabel.setForeground(Color.red);
                    isRunning = false; 
                }
                System.out.println(1);
            }
            else {
                inputPort.setText("");
                inputPort.setEditable(true);
                switchButton.setText("Start");
                informLabel.setText("");
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                        stop();
                    } catch (IOException ex) {
                        Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    isRunning = false; 
                    System.out.println(0);
                }
            }
        }
        
    }
    
    class ServerRunning extends Thread {
        @Override
        public void run() {
            informLabel.setText("Server is waiting on port " + inputPort.getText());
            informLabel.setForeground(Color.green);
            switchButton.setText("Stop");
            inputPort.setEditable(false);
            while (true) {
                
                try { 
                    Socket socket = serverSocket.accept();
                    ClientThread ct = new ClientThread(socket); 
                    ar.add(ct); 
                    ct.start();
                } catch (IOException ex) {
                    //System.out.println("Error in line 70");
                    //Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        class ClientThread extends Thread {
            private ObjectInputStream sInput;
            private Socket socket; 
            private ObjectOutputStream sOutput; 
            private String username; 

            public ClientThread(Socket socket) {
                //System.out.println("line 85");
                this.socket = socket; 
                try { 
                    sOutput = new ObjectOutputStream(socket.getOutputStream()); 
                    sInput = new ObjectInputStream(socket.getInputStream());
                    try { 
                        username = (String) sInput.readObject();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    //System.out.println("Line 84");
                    //Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            @Override
            public void run() {
                while (true) {
                    try { 
                        ChatMessage cm = (ChatMessage) sInput.readObject();
                        //System.out.println("Server received: " + cm.getType());
                        //System.out.println(cm.getMessage());
                        if (cm.getType() == 1)
                            broadcast(cm); 
                        else break; 
                    } catch (IOException ex) {
                        System.out.println("Line 98");
                        Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        System.out.println("Line 101");
                        Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }             
                }
                removeClient(username); 
            }
            
            public void sendMessage(ChatMessage message) {
                try {
                    sOutput.writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            public void disconnect() {
                if (socket != null) try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (sInput != null) try {
                    sInput.close();
                } catch (IOException ex) {
                    Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (sOutput != null) try {
                    sOutput.close();
                } catch (IOException ex) {
                    Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        }
    }
}
