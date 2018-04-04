/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomplete;

/**
 *
 * @author taba
 */
public class UserPacket {
    private String username; 
    private int status; 

    public UserPacket(String username, int status) {
        this.username = username;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public int getStatus() {
        return status;
    }
    
    
}
