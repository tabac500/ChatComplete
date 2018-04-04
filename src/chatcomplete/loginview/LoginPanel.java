/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomplete.loginview;
import java.awt.*;
import java.awt.event.KeyAdapter;
import javax.swing.*; 
import java.awt.event.*; 
/**
 *
 * @author taba
 */
public class LoginPanel extends JPanel {
    public JTextField usernameArea; 
    public JPasswordField passwordArea; 
    public LoginPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel jLabel1, jLabel2; 
        usernameArea = new JTextField(20);   //login Field
        Font font = new Font("Verdana", Font.PLAIN, 16);    //font 
        usernameArea.setFont(font);      //add font to login field
        passwordArea = new JPasswordField(20);   //pasword Field
        passwordArea.setFont(font);        //add font to password field  
        jLabel1 = new JLabel("Username");   
        jLabel1.setFont(font);
        jLabel2 = new JLabel("Password"); 
        jLabel2.setFont(font);
        //jLabel1.setAlignmentX(Component.BOTTOM_ALIGNMENT);
        //add Label1 to Main, and keep position
        JPanel label1Panel = new JPanel();
        label1Panel.setLayout(new BoxLayout(label1Panel, BoxLayout.X_AXIS));
        label1Panel.add(jLabel1);
        label1Panel.add(Box.createHorizontalGlue());
        this.add(label1Panel); 
        
        this.add(usernameArea);          //add userName area 

        
        //add Label2 to Main, and keep position 
        JPanel label2Panel = new JPanel(); 
        label2Panel.setLayout(new BoxLayout(label2Panel, BoxLayout.X_AXIS));
        label2Panel.add(jLabel2);
        jLabel2.setAlignmentX(LEFT_ALIGNMENT);
        label2Panel.add(Box.createHorizontalGlue()); 
        
        
        this.add(label2Panel);
        
        //username must be less or equals 20 character 
        usernameArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (usernameArea.getText().length() >= 20) 
                    e.consume(); 
            }
        });
   
        this.add(passwordArea); 
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        JFrame testFrame = new JFrame("Test Login"); 
        LoginPanel loginPanel = new LoginPanel(); 
        testFrame.setSize(200, 200);
        testFrame.add(loginPanel);
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setVisible(true);
    }

    public JTextField getUsernameArea() {
        return usernameArea;
    }

    public JPasswordField getPasswordArea() {
        return passwordArea;
    }
    
}
