/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomplete.userview;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*; 
import chatcomplete.ConnectDatabase; 
/**
 *
 * @author taba
 */
public class AdminToolFrame extends JFrame{
    public AdminToolFrame() {
        this.setLayout(new GridLayout(2, 1));
        this.setSize(300, 300);
        JPanel addUserPanel = new JPanel(); 
        addUserPanel.setLayout(new GridLayout(1, 3));
        JTextField addUserUsername = new JTextField(); 
        JPasswordField addUserPassword = new JPasswordField(); 
        JButton addUserButton = new JButton("Signup"); 
        addUserPanel.add(addUserUsername);
        addUserPanel.add(addUserPassword);
        addUserPanel.add(addUserButton); 
        this.add(addUserPanel);
        JPanel deleteUserPanel = new JPanel(); 
        deleteUserPanel.setLayout(new GridLayout(1, 3));
        deleteUserPanel.add(new JPanel());
        deleteUserPanel.add(new JPanel());
        JButton deleteUserButton = new JButton("Delete");
        deleteUserPanel.add(deleteUserButton);
        this.add(deleteUserPanel); 
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setVisible(false);
        
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addUserUsername.getText().equalsIgnoreCase("") || 
                        String.valueOf(addUserPassword.getPassword()).equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Username and password must not be empty");
                } else {
                    if (!ConnectDatabase.addUser(addUserUsername.getText(), 
                            String.valueOf(addUserPassword.getPassword()))) 
                        JOptionPane.showMessageDialog(null, "Username has already exist"); 
                    else { 
                            JOptionPane.showMessageDialog(null, "Add new user succesfully");
                    }
                    
                }
                addUserUsername.setText("");
                addUserPassword.setText("");
            }
        });
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addUserUsername.getText().equalsIgnoreCase("") || 
                        String.valueOf(addUserPassword.getPassword()).equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Username and password must not be empty");
                } else {
                    if (!ConnectDatabase.deleteUser(addUserUsername.getText(), 
                            String.valueOf(addUserPassword.getPassword()))) 
                        JOptionPane.showMessageDialog(null, "Username isn't exist"); 
                    else { 
                            JOptionPane.showMessageDialog(null, "Delete user succesfully");
                    }
                    
                }
                addUserUsername.setText("");
                addUserPassword.setText("");
            }
        });
    }
    public static void main(String[] args) {
        new AdminToolFrame(); 
    }
}
