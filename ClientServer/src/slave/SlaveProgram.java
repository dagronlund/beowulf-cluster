package slave;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
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

    public SlaveProgram(String ip) throws IOException {
        this(ip, Network.PORT);
    }

    public SlaveProgram(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        if (!Network.handshake(socket)) {
            throw new IOException();
        }
        while (!done) {
            try {
                int response = handshake();
                if (response == Network.TASK_READY) {
                    runTask();
                } else if (response == -1) {
                    commError();
                }
            } catch (IOException ex) {
            }
        }
    }

    private int handshake() throws IOException {
        int response = -1;
        long start = System.currentTimeMillis();
        while (response == -1 && !Network.timedOut(start)) {
            if (socket.getInputStream().available() > 0) {
                response = socket.getInputStream().read();
            }
        }
        if (response != -1) {
            socket.getOutputStream().write(Network.ACK);
        }
        return response;
    }

    private void runTask() throws IOException {
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
    }

    private void commError() {
        System.out.println("Error -- Communication -- Fatal");
        System.out.println("Response was not recieved from the server.");
        System.out.println("Slave shutting down.");
        done = true;
    }

    public static void main(String[] args) throws IOException {
        String ip = new Scanner(System.in).nextLine();
        SlaveProgram program = new SlaveProgram(ip);
    }
}
