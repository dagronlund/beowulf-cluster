package slave;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import main.programStructure.Network;
import main.programStructure.Task;

/**
 *
 * @author abell
 */
public class SlaveProgram {

    private ArrayList<Task> taskList = new ArrayList<Task>();
    private Socket socket;
    private int id;

    public SlaveProgram(String ip) throws IOException {
        socket = new Socket(ip, Network.PORT);
        socket.getOutputStream().write(Network.HANDSHAKE);
        if (socket.getInputStream().read() != Network.HANDSHAKE) {
            throw new IOException();
        }
        byte[] idBytes = new byte[4];
        socket.getInputStream().read(idBytes);
        id = (idBytes[0] << 24) | (idBytes[1] << 16) | (idBytes[2] << 8) | idBytes[3];
    }
    
    public int getID() {
        return id;
    }
    
    public static void main(String[] args) throws IOException {
        SlaveProgram program = new SlaveProgram(new Scanner(System.in).next());
    }
}
