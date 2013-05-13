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
 *
 * @author abell
 */
public class Server {
    int port;
    
    ServerMain.Server bigBrother = new ServerMain.Server();
    
    public Server(){
        port = 1234;
        bootup();
    }
    
    public Server(int po){
        port = po;
        bootup();
    }
    
    public void bootup(){
        System.out.println("Starting Server...");
        if(starter() == 1)
            System.out.println("Success!");
        else if(starter() == 0)
            System.out.println("Awwww..... Crap.");
        
    }
    
    public int starter(){
        try {
            ServerSocket ssk = new ServerSocket(port);
            Socket sk = ssk.accept();
            Scanner in = new Scanner(sk.getInputStream());
            PrintWriter out = new PrintWriter(sk.getOutputStream(), true);
            OutputStream os = sk.getOutputStream();
            bigBrother.addSlave(sk.getInetAddress().getHostName(), sk.getInetAddress().getHostAddress());
            return 1;
//            String s = in.nextLine();
//            System.out.println("Server recv: " + s);
//            out.println(s);
//            System.out.println("Server Success");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
}
