package slave;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import runtime.JarUnpacker;
import main.program.Network;
import main.program.PacketMap;
import runtime.UserCodeFactory;
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
                    int response = -1;
                    while (response == -1) {
                        if (socket.getInputStream().available() > 0) {
                            response = socket.getInputStream().read();
                        }
                    }
                    if (response == Slave.HEARTBEAT) {
                        socket.getOutputStream().write(Slave.ACK);
                    }
                    if (response == Slave.TASK_READY) {
                        InputStream in = socket.getInputStream();
                        String taskId = Network.readString(in);
                        byte[] jarData = Network.readData(in);
                        PacketMap map = null;
                        if (Network.readInt(in) == 1) {
                            map = new PacketMap();
                            map.receive(in);
                        }
                        in.read(); // Catches the END_DATA flag
                        System.out.println("Slave Recieved Task");
                        JarUnpacker j = new JarUnpacker(jarData, "task_code");
                        j.extract();
                        UserCodeFactory userCode =
                                new UserCodeFactory(j.getExtractDirectory());
                        PacketMap result = userCode.getUserTask(taskId).run(map);
                        result.send(socket.getOutputStream());
                    }
                } catch (IOException ex) {
                }
            }
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
        SlaveProgram program = new SlaveProgram("localhost");
    }
}
