/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomplete;
import java.sql.*; 
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList; 
/**
 *
 * @author taba
 */

//NULL - Chua dang nhap; 
//-2 : Dang nhap nhung chua vao phong 
// > 0: Da vao phong 
public class ConnectDatabase {
    //get all users in database
    public static ArrayList<UserPacket> getUsers() {
        ArrayList<UserPacket> ar = new ArrayList<UserPacket>(); 
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection 
                ("jdbc:mysql://localhost:3306/chat_complete", "root", "");
            Statement stmt = con.createStatement(); 
            ResultSet rs = stmt.executeQuery("select username, status from information"); 
            while (rs.next()) 
                ar.add(new UserPacket(rs.getString(1), rs.getInt(2)));
                //System.out.println(rs.getString(1) + " " + rs.getInt(2));
            con.close();
        } catch (Exception ex) {
        }
        return ar; 
    }
    
    //getUsers is on Port 
    public static ArrayList<UserPacket> getUsersonPort(String port) {
        ArrayList<UserPacket> ar = new ArrayList<UserPacket>(); 
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/chat_complete", "root", "");
            Statement stmt = con.createStatement(); 
            ResultSet rs = stmt.executeQuery("select username, status from information where status = " + port); 
            while (rs.next()) {
                ar.add(new UserPacket(rs.getString(1), rs.getInt(2))); 
            }
        } catch (Exception ex) {
        }
        return ar; 
    }
    
    //get all users is active 
    public static ArrayList<UserPacket> getUsersIsActive() {
        ArrayList<UserPacket> ar = new ArrayList<UserPacket>(); 
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/chat_complete", "root", ""); 
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select username, status from information where status is not NULL"); 
            while (rs.next()) {
                ar.add(new UserPacket(rs.getString(1), rs.getInt(2))); 
            }
            con.close();
        } catch (Exception e) {
        }
        return ar; 
    }
    
    public static ArrayList<UserPacket> getPortOfUser(String username) {
        ArrayList<UserPacket> ar = new ArrayList<UserPacket>(); 
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/chat_complete", "root", ""); 
            Statement stmt = con.createStatement(); 
            ResultSet rs = stmt.executeQuery("select username, status from information where username =\"" + username + "\""); 
            while (rs.next())
                ar.add(new UserPacket(rs.getString(1), rs.getInt(2))); 
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ar; 
    }
    
    public static int VerifyUser(String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/chat_complete", "root", ""); 
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select status from information where username=\"" +
            username + "\" and password =\"" + password + "\""); 
            while (rs.next()) {
                 System.out.println(rs.getInt(1));
                 return (rs.getInt(1)); 
            }
        } catch (Exception e) {
        }
        return -1; 
    }
    
    public static void updateStatus(String username, int status) {
        try {
            String sta = Integer.toString(status); 
            if (sta.equalsIgnoreCase("0")) 
                sta = "NULL";
            //System.out.println(sta);
            Class.forName("com.mysql.jdbc.Driver"); 
            Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/chat_complete","root", ""); 
            PreparedStatement stmt = con.prepareStatement(
            "update information set status=" + sta + " where username='" + username + "'");
            stmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static boolean addUser(String username, String password) {
        try { 
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/chat_complete", "root", "");   
            PreparedStatement stmt = con.prepareStatement(
            "insert into information(username, password) values('" + username + "','" + password + "')"); 
            stmt.executeUpdate();
            return true; 
        } catch (Exception e) {
            System.out.println(e);
        }
        return false; 
    }
    
    public static boolean deleteUser(String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/chat_complete", "root", ""); 
            PreparedStatement stmt = con.prepareStatement(
            "delete from information where username='" + username + "' and password='" + password + "'"); 
            stmt.executeUpdate(); 
            return true; 
        } catch (Exception e) {
        }
        return false; 
    }
    
    public static boolean isAdmin(String username) {
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhot:3306/chat_complete", "root", ""); 
            Statement stmt = con.createStatement(); 
            ResultSet rs = stmt.executeQuery("select admin from information where username='" + username + "'"); 
            while (rs.next()) {
                if (rs.getInt(1) == 1) return true;
                else return false; 
            }
        } catch (Exception e) {
        }
        return false; 
    }
    
    public static void main(String[] args) {
        //ConnectDatabase.getUsers();
        //ConnectDatabase.getUsersonPort("1234");
        //ConnectDatabase.getUsersIsActive();
        //ConnectDatabase.getPortOfUser("tamhoang");
        //ConnectDatabase.VerifyUser("taba", "tabac500");
        ConnectDatabase.updateStatus("taba", -2);
        ConnectDatabase.deleteUser("honggiang", "honggiangc500");
    }
}
