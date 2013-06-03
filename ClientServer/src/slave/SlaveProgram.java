package slave;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import runtime.JarUnpacker;
import main.program.Network;
import main.program.PacketMap;
import runtime.UserCodeFactory;

/**
 *
 * @author abell
 */
public class SlaveProgram {

    private Socket socket;
    private boolean done = false;
    private Runnable listener = new Runnable() {
        @Override
        public void run() {
            while (!done) {
                try {
                    int response = -1;
                    long start = System.currentTimeMillis();
                    while (response == -1 && !Network.timedOut(start)) {
                        if (socket.getInputStream().available() > 0) {
                            response = socket.getInputStream().read();
                        }
                    }
                    if (response == Network.HEARTBEAT) {
                        socket.getOutputStream().write(Network.ACK);
                    } else if (response == Network.TASK_READY) {
                        socket.getOutputStream().write(Network.ACK);
                        InputStream in = socket.getInputStream();
                        String taskId = Network.readString(in);
                        byte[] jarData = Network.readData(in);
                        PacketMap map = null;
                        if (Network.readInt(in) == 1) {
                            map = new PacketMap();
                            map.receive(in);
                        }
                        JarUnpacker j = new JarUnpacker(jarData, "task_code");
                        j.extract();
                        UserCodeFactory userCode =
                                new UserCodeFactory(j.getExtractDirectory());
                        PacketMap result = userCode.getUserTask(taskId).run(map);
                        result.send(socket.getOutputStream());
                    } else if (response == -1) {
                        System.out.println("Error -- Communication -- Fatal");
                        System.out.println("Response was not recieved from the server.");
                        System.out.println("Slave shutting down.");
                        done = true;
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

    public static void main(String[] args) throws IOException {
        SlaveProgram program = new SlaveProgram("localhost");
    }
}
