/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomplete;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel; 
import javax.swing.JFrame; 
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
/**
 *
 * @author taba
 */
public class ChatMessage implements Serializable{
    private String username, message;
    private int type; 
    public static final int MESSAGE = 1, LOGOUT = 2; 
    private File image; 
    JLabel imageLabel = null; 
    BufferedImage bufferedImage; 
    String imageDirection = null; 

    public ChatMessage(String username, String message, int type, File image) {
        this.username = username;
        this.message = message;
        this.type = type;
        this.image = image; 
    }
    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }
    
    public JPanel toPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(username));
        JTextArea textArea = new JTextArea();
        textArea.setSize(400, 300);
        
        textArea.setLineWrap(true);
        textArea.setText(message);
        //textArea.setSize(textArea.getPreferredSize());
        //panel.add(new JLabel(message));
        panel.add(textArea); 
//        panel.setPreferredSize(panel.getPreferredSize());
//        panel.setMaximumSize(panel.getPreferredSize());
        bufferedImage = null;
        if (image != null) {
            textArea.setText(username + " has sended an image");
            JButton imageViewer = new JButton(); 
            try {
                BufferedImage img = ImageIO.read(image);
                Image newIcon = img.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                imageViewer.setIcon(new ImageIcon(newIcon));
            } catch (IOException ex) {
                Logger.getLogger(ChatMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
            panel.add(imageViewer);
            imageViewer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try { 
                        BufferedImage img = ImageIO.read(image);                         
                        ImageIcon icon = new ImageIcon(img); 
                        JOptionPane.showMessageDialog(null, "", "Image Viewer", MESSAGE, icon);
                        //JOptionPane.showMessageDialog(null, "Show image");
                    } catch (IOException ex) {
                        Logger.getLogger(ChatMessage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Test1 test = new Test1(image); 
                    //test.start();
                }
            });
        }
        panel.setPreferredSize(panel.getPreferredSize());
        panel.setMaximumSize(panel.getPreferredSize());
        return panel; 
    }
    
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame(); 
        mainFrame.setSize(400, 400);
        JPanel ab = new JPanel(); 
        ab.add((new ChatMessage("taba", "Hoang Phuc", 1, null)).toPanel());
        mainFrame.add(ab); 
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
