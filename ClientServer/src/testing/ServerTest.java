package testing;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.programStructure.NetworkPackets;

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
            
            NetworkPackets packets = new NetworkPackets();
            packets.receive(sk.getInputStream());
            System.out.println(packets.getPacket("Hello").getData()[0]);
        } catch (IOException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
