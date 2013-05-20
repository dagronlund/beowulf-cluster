package slave;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.UserProgramLoader;
import main.programStructure.Network;
import main.programStructure.PacketMap;
import main.programStructure.Task;

/**
 *
 * @author abell
 */
public class SlaveProgram {

    private Socket socket;
    private int id;
    private Runnable listener = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    InputStream in = socket.getInputStream();
                    while (in.read() != Network.TASK_START) {
                    }
                    System.out.println("Task Found");
                    int id = Network.readInt(in);
                    String className = Network.readString(in);
                    byte[] jarData = Network.readData(in);
                    PacketMap map;
                    if (Network.readInt(in) == 1) {
                        map = new PacketMap();
                        map.receive(in);
                    }
                    in.read(); // Catches the END_DATA flag.
                    //Task task = new Task(id, className, jarData);
                    UserProgramLoader loader = new UserProgramLoader(jarData);
                    
                } catch (IOException ex) {
                    Logger.getLogger(SlaveProgram.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    public SlaveProgram(String ip) throws IOException {
        socket = new Socket(ip, Network.PORT);
        if (!Network.handshake(socket)) {
            throw new IOException();
        }
        byte[] idBytes = new byte[4];
        socket.getInputStream().read(idBytes);
        id = (idBytes[0] << 24) | (idBytes[1] << 16) | (idBytes[2] << 8) | idBytes[3];
        new Thread(listener).start();
    }

    public int getID() {
        return id;
    }

    public static void main(String[] args) throws IOException {
        SlaveProgram program = new SlaveProgram(new Scanner(System.in).next());
    }
}
