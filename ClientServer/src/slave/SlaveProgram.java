package slave;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.JarUnpacker;
import main.UserProgramClassLoader;
import main.programStructure.Network;
import main.programStructure.PacketMap;
import main.programStructure.Task;
import server.Slave;

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
                    int response = 0;
                    while (response == 0) {
                        if (socket.getInputStream().available() > 0) {
                            response = socket.getInputStream().read();
                        }
                    }
                    socket.getOutputStream().write(Slave.ACK);
                    if (response == Slave.TASK_READY) {
                        InputStream in = socket.getInputStream();
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
                        JarUnpacker j = new JarUnpacker(jarData, "task_code");
                        j.extract();
                        UserProgramClassLoader loader =
                                new UserProgramClassLoader(
                                this.getClass().getClassLoader(),
                                j.getExtractDirectory().toString() + className);

                    }
                } catch (IOException ex) {
                }


            }
//            while (true) {
//                try {
//                    InputStream in = socket.getInputStream();
//                    while (in.read() != Network.TASK_START) {
//                    }
//                    System.out.println("Task Found");
//                    
//                } catch (IOException ex) {
//                    Logger.getLogger(SlaveProgram.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
        }
    };

    public SlaveProgram(String ip) throws IOException {
        socket = new Socket(ip, Network.PORT);
        if (!Network.handshake(socket)) {
            throw new IOException();
        }
        new Thread(listener).start();
    }

    public SlaveProgram(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        if (!Network.handshake(socket)) {
            throw new IOException();
        }
        new Thread(listener).start();
    }

    public int getID() {
        return id;
    }

    public static void main(String[] args) throws IOException {
        SlaveProgram program = new SlaveProgram(new Scanner(System.in).next());
    }
}
