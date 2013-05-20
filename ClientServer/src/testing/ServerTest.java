package testing;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.programStructure.Network;

/**
 * @author David Gronlund
 */
public class ServerTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Server Online");
        try {
            ServerSocket ssk = new ServerSocket(Network.PORT);
            Socket sk = ssk.accept();
            Thread.sleep(2000);
            System.out.println(Network.handshake(sk));
        } catch (IOException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
