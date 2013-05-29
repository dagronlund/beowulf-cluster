package testing;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.program.DataPacket;
import main.program.Network;
import main.program.PacketMap;

/**
 * @author David Gronlund
 */
public class ClientTest {

    public static void main(String[] args) {
        try {
            Socket sk = new Socket(InetAddress.getByName("localhost"), Network.PORT);
            System.out.println(Network.handshake(sk));
        } catch (ConnectException ce) {
            System.out.println("No server found at that ip.");
        } catch (IOException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
