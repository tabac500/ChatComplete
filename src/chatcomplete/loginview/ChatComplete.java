/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomplete.loginview;
import chatcomplete.userview.GUIClientWelcome;
import java.awt.*;
import javax.swing.*; 
import chatcomplete.ClientGUI;
/**
 *
 * @author taba
 */
public class ChatComplete extends JFrame {
    //public static ChatComplete mainFrame;
    public static ClientGUI x; 
    private final JPanel jPanel1; 
    private final LoginPanel jPanel2; 
    private final SubmitPanel jPanel3; 
    public ChatComplete() {
        this.setSize(300, 500);
        this.setLayout(new GridLayout(3, 1));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1 = new JPanel(); 
        jPanel1.setBackground(Color.red);
        jPanel2 = new LoginPanel();       //Panel for input
        jPanel3 = new SubmitPanel(jPanel2);  //Panel for submit input 
        add(jPanel1); 
        add(jPanel2); 
        add(jPanel3); 
        this.setResizable(false);
        this.setVisible(true);
        if (x != null) 
            x.dispose();
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public LoginPanel getjPanel2() {
        return jPanel2;
    }

    public SubmitPanel getjPanel3() {
        return jPanel3;
    }
    
    
    /**ta
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ChatComplete chatComplete = new ChatComplete(); 
        GUIClientWelcome.a = chatComplete; 
    } 
    
}
