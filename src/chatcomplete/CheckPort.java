/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomplete;
import java.io.*;
import java.net.*;
/**
 *
 * @author taba
 */
public class CheckPort {
    public static boolean isPortAvailable(int port) {
        boolean result = false; 
        try {
            (new Socket("localhost", port)).close();
            result = true; 
        } catch (Exception e) {
        }
        return result; 
    }
}
