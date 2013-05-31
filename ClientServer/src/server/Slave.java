package server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.program.Network;
import main.program.PacketMap;
import runtime.TaskPackage;

/**
 *
 * @author abell
 */
public class Slave {

    public static final int READY = 1;
    public static final int BUSY = 2;
    public static final int OFFLINE = 3;
    //
    public static final byte HEARTBEAT = 3;
    public static final byte TASK_READY = 4;
    public static final byte ACK = 5;
    //
    private AtomicInteger state;
    private TaskPackage task;
    private PacketMap map;
    private Socket socket;
    private String address;
    private Runnable listener = new Runnable() {
        @Override
        public void run() {
            while (state.get() == READY) {
                synchronized (state) {
                    if (task == null) {
                        try {
                            socket.getOutputStream().write(HEARTBEAT);
                            long start = System.currentTimeMillis();
                            state.set(OFFLINE);
                            while (!Network.timedOut(start) && state.get() == OFFLINE) {
                                if (socket.getInputStream().available() > 0
                                        && socket.getInputStream().read() == ACK) {
                                    state.set(READY);
                                }
                            }
                        } catch (IOException ex) {
                        }
                    } else {
                        try {
                            socket.getOutputStream().write(TASK_READY);
                            while (socket.getInputStream().available() < 1
                                    && socket.getInputStream().read() != ACK) {
                            }
                            state.set(BUSY);
                            System.out.println("Server sent Task");
                            map = Network.executeTask(socket, task, map);
                            task = null;
                            state.set(READY);
                        } catch (IOException ex) {
                        }
                    }
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Slave.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    public Slave(Socket socket) throws IOException {
        System.out.println("Slave Connected");
        this.socket = socket;
        address = socket.getInetAddress().getHostAddress();
        if (!Network.handshake(socket)) {
            throw new IOException();
        }
        state = new AtomicInteger(READY);
        new Thread(listener).start();
    }

    public String getAddress() {
        return address;
    }

    public Socket getConnection() {
        return socket;
    }

    public int getState() {
        return state.get();
    }

    public void shutdown() {
        state.set(OFFLINE);
    }

    public PacketMap runTask(TaskPackage task, PacketMap map) {
        while (state.get() != READY) {
        }
        System.out.println("Slave Object is running task");
        this.task = task;
        this.map = map;
        while (state.get() == BUSY) {
        }
        System.out.println("Slave Object recieved result");
        return map;
    }

    @Override
    public String toString() {
        return socket.getInetAddress().toString();
    }
}
