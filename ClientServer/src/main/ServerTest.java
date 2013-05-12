package main;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David Gronlund
 */
public class ServerTest {
    
    public static final int PORT = 1235;

    public static void main(String[] args) {
        System.out.println("Server Online");
        try {
            ServerSocket ssk = new ServerSocket(PORT);
            Socket sk = ssk.accept();
            Scanner in = new Scanner(sk.getInputStream());
            PrintWriter out = new PrintWriter(sk.getOutputStream(), true);
            OutputStream os = sk.getOutputStream();
            
            
            String s = in.nextLine();
            System.out.println("Server recv: " + s);
            out.println(s);
            System.out.println("Server Success");
        } catch (IOException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
