/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomplete;
import chatcomplete.userview.GUIClientWelcome;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import chatcomplete.loginview.ChatComplete; 
import chatcomplete.userview.ToolFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.im.InputContext;
import java.awt.image.BufferedImage; 
import java.net.*; 
import java.io.*; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;
/**
 *
 * @author taba
 */
public class ClientGUI extends JFrame {
    Socket socket; 
    ObjectInputStream sInput;
    ObjectOutputStream sOutput; 
    String username; 
    int port; 
    JPanel resultArea; 
    JScrollPane resultAreaPart; 
    public static GUIClientWelcome x;
    JButton sendImageButton; 
    JTextArea inputText; 
    public ClientGUI(String username, int port) {
        ToolFrame toolFrame = new ToolFrame(); 
        this.port = port;
        this.username = username; 
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(500, 600);
        JPanel welcomePart = new JPanel();
        welcomePart.setLayout(new BoxLayout(welcomePart, BoxLayout.X_AXIS));
        welcomePart.add(new JLabel("Hello " + username + ". You are on port " + port));
        welcomePart.add(Box.createHorizontalGlue());
        this.add(welcomePart); 
        
        resultArea = new JPanel();
        resultArea.setLayout(new BoxLayout(resultArea, BoxLayout.Y_AXIS));
        resultAreaPart = new JScrollPane(resultArea); 
        resultAreaPart.setPreferredSize(new Dimension(350, 400));
        this.add(resultAreaPart); 
        
        JPanel inputPanel = new JPanel(); 
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputText = new JTextArea();
        inputText.setLineWrap(true);
        inputText.addKeyListener(new HandlerInputText());
        JScrollPane inputTextScroll = new JScrollPane(inputText); 
        inputPanel.add(inputTextScroll); 
        sendImageButton = new JButton("Send Image"); 
        inputPanel.add(sendImageButton); 
        this.add(inputPanel); 
        
        
        JPanel toolPart = new JPanel();
        JButton logoutButton = new JButton("Logout"); 
        JButton outRoomButton = new JButton("Out room");
        JButton linkToolButton = new JButton("Show Advanced"); 
        toolPart.setLayout(new GridLayout(1, 3));
        toolPart.add(logoutButton);
        toolPart.add(outRoomButton);
        toolPart.add(linkToolButton); 
        
        linkToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toolFrame.isVisible()) {
                    toolFrame.setVisible(false);
                    linkToolButton.setText("Show Advanced");
                }
                else {
                    toolFrame.setVisible(true);
                    linkToolButton.setText("Hide Advanced");
                }
            }
        });
        
        sendImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                BufferedImage image = null; 
                jfc.setDialogTitle("Select an image you want to send");
                jfc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and GIF iamges", "png", "gif"); 
                jfc.addChoosableFileFilter(filter);
                
                int val = jfc.showOpenDialog(null); 
                if (val == JFileChooser.APPROVE_OPTION) {
                    System.out.println(jfc.getSelectedFile().getPath());
                    File file = jfc.getSelectedFile(); 
                    try {
                        sOutput.writeObject(new ChatMessage(username, "", 1, file));
                    } catch (IOException ex) {
                        Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                }
            }
        });
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectDatabase.updateStatus(username, 0);
                try {
                    sOutput.writeObject(new ChatMessage(username, "", 2, null));
                } catch (IOException ex) {
                    Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                ChatComplete.main(null);
                disconnect();
                CloseThisFrame();
            }
        });
        outRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectDatabase.updateStatus(username, -2);
                try {
                    sOutput.writeObject(new ChatMessage(username, "", 2, null));
                } catch (IOException ex) {
                    Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                String args[] = {username}; 
                GUIClientWelcome.main(args);
                disconnect();
                CloseThisFrame();
            }
        });
        this.add(toolPart); 
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                ConnectDatabase.updateStatus(username, 0);
                disconnect();
                toolFrame.dispose();
                CloseThisFrame();
            }
        });
        x.dispose();
        new ClientThread().start();
    }
    
    void disconnect() {
        if (socket != null) try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        } 
        if (sInput != null) try {
            sInput.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (sOutput != null) try {
            sOutput.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    class HandlerInputText implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                //System.out.println(inputText.getText());
                if (sOutput != null) {
                    try {
                        sOutput.writeObject(new ChatMessage(username, inputText.getText(), 1, null));
                    } catch (IOException ex) {
                        Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                inputText.setText("");
                e.consume();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
        
    }
    
    private void append(ChatMessage msg) {
        System.out.println("Append msg");
        JPanel msgPanel = msg.toPanel(); 
        JPanel holdPanel = new JPanel(); 
        holdPanel.setLayout(new BoxLayout(holdPanel, BoxLayout.X_AXIS));
        if (msg.getUsername().equalsIgnoreCase(username)){
            //holdPanel.setAlignmentX(RIGHT_ALIGNMENT);
            holdPanel.add(Box.createHorizontalGlue()); 
            msgPanel.setBackground(Color.green);
            holdPanel.add(msgPanel);
        } else {
            msgPanel.setBackground(Color.MAGENTA);      
            //holdPanel.setAlignmentX(LEFT_ALIGNMENT);
            holdPanel.add(msgPanel);
            holdPanel.add(Box.createHorizontalGlue()); 
        }
        //msgPanel.setBackground(Color.red);
        resultArea.add(holdPanel); 
        resultArea.add(Box.createRigidArea(new Dimension(0, 10))); 
        resultArea.updateUI(); 
        resultAreaPart.validate();
        JScrollBar vertical = resultAreaPart.getVerticalScrollBar(); 
        vertical.setValue( vertical.getMaximum());
}
  
    class ClientThread extends Thread {
        @Override
        public void run() {
            try {
                socket = new Socket("localhost", port);
            } catch (IOException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println("OK");
            try { 
                sInput = new ObjectInputStream(socket.getInputStream());
                sOutput = new ObjectOutputStream(socket.getOutputStream()); 
            } catch (IOException ex) {
              Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            new ListenFromServer().start(); 
        } 
        
        class ListenFromServer extends Thread {
            @Override
            public void run() {
                try {
                    sOutput.writeObject(username);
                } catch (IOException ex) {
                    Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                } 
                while (true) {
                    try { 
                        ChatMessage msg = (ChatMessage) sInput.readObject();
                        append(msg); 
                        //resultArea.add(msg.toPanel()); 
                        System.out.println(msg.getMessage());
                    } catch (IOException ex) {
                        Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                        inputText.setEditable(false);
                        sendImageButton.setEnabled(false);
                        inputText.setText("Server Crashed");
                        disconnect(); 
                        break; 
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    public void CloseThisFrame() {
        super.dispose();
    }
    public static void main(String[] args) {
        ClientGUI clientGUI = new ClientGUI(args[0], Integer.parseInt(args[1])); 
        ChatComplete.x = clientGUI; 
    }
}
