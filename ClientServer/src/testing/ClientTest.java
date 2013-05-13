package testing;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.programStructure.DataPacket;
import main.programStructure.NetworkPackets;

/**
 * @author David Gronlund
 */
public class ClientTest {

    public static final int PORT = 1235;

    public static void main(String[] args) {
        try {
            Socket sk = new Socket(InetAddress.getByName("localhost"), PORT);

            NetworkPackets packets = new NetworkPackets();
            packets.addPacket(new DataPacket("Hello", new byte[]{1, 2, 3, 4}));
            packets.send(sk.getOutputStream());

        } catch (ConnectException ce) {
            System.out.println("No server found at that ip.");
        } catch (IOException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
