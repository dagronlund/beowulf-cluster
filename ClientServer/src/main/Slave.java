package main;

import java.awt.Toolkit;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abell
 */
public class Slave {
    String address;
    int port;
    
    public Slave(){
        address = "localhost";
        port = 1234;
        bootup();
    }
    
    public Slave(String ad, int po){
        address = ad;
        port = po;
        bootup();
    }
    
    public void bootup(){
        System.out.println("Starting Slave...");
        if(starter() == 1)
            System.out.println("Success!");
        else if(starter() == 0)
            System.out.println("Awwww..... Crap.");
    }
    
    public int starter(){
        try {
            Socket sk = new Socket(InetAddress.getByName(address), port);
            Scanner in = new Scanner(sk.getInputStream());
            PrintWriter out = new PrintWriter(sk.getOutputStream(), true);
            slave.SlaveProgram sla = new slave.SlaveProgram();
            sla.MASTER(address);
            Toolkit.getDefaultToolkit().beep();
            return 1;
        } catch (ConnectException ce) {
            System.out.println("No server found at that ip.");
            return 0;
        }catch (IOException ex) {
            Logger.getLogger(Slave.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } 
    }
}
