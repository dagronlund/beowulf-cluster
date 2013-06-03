package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.program.Network;
import static main.program.Network.writeData;
import static main.program.Network.writeInt;
import static main.program.Network.writeString;
import main.program.PacketMap;
import runtime.TaskPackage;

/**
 *
 * @author abell
 */
public class Slave {

    public static final int FREE = 1;
    public static final int TASK_ADDED = 2;
    public static final int TASK_COMPLETE = 3;
    private AtomicInteger status;
    private AtomicInteger schedule;
    private TaskPackage task;
    private PacketMap map;
    private Socket socket;
    private int ping = 0;
    private Runnable listener = new Runnable() {
        @Override
        public void run() {
            while (status.get() == Network.ONLINE) {
                if (schedule.get() == FREE) {
                    synchronized (status) {
                        try {
                            socket.getOutputStream().write(Network.HEARTBEAT);
                            long start = System.currentTimeMillis();
                            status.set(Network.OFFLINE);
                            while (!Network.timedOut(start) && status.get() == Network.OFFLINE) {
                                if (socket.getInputStream().available() > 0
                                        && socket.getInputStream().read() == Network.ACK) {
                                    ping = (int) (System.currentTimeMillis() - start);
                                    status.set(Network.ONLINE);
                                }
                            }
                        } catch (IOException ex) {
                        }
                    }
                } else if (schedule.get() == TASK_ADDED) {
                    try {
                        socket.getOutputStream().write(Network.TASK_READY);
                        while (socket.getInputStream().available() < 1
                                && socket.getInputStream().read() != Network.ACK) {
                        }
                        OutputStream out = socket.getOutputStream();
                        writeString(out, task.getTaskId());
                        writeData(out, task.getJarData());
                        if (map != null) {
                            writeInt(out, 1);
                            map.send(out);
                        } else {
                            writeInt(out, 0);
                        }
                        PacketMap result = new PacketMap();
                        result.receive(socket.getInputStream());
                        map = result;
                        schedule.set(TASK_COMPLETE);
                    } catch (IOException ex) {
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
    Thread t;

    public Slave(Socket socket) throws IOException {
        this.socket = socket;
        if (!Network.handshake(socket)) {
            throw new IOException();
        }
        status = new AtomicInteger(Network.ONLINE);
        schedule = new AtomicInteger(FREE);
        t = new Thread(listener);
        t.start();
    }

    public Socket getSocket() {
        return socket;
    }

    public int getStatus() {
        return status.get();
    }
    
    public int getPing() {
        return ping;
    }

    public void shutdown() {
        status.set(Network.OFFLINE);
    }

    public PacketMap runTask(TaskPackage task, PacketMap packetData) {
        while (schedule.get() != FREE) {
        }
        this.task = task;
        map = packetData;
        schedule.set(TASK_ADDED);
        while (schedule.get() != TASK_COMPLETE) {
        }
        schedule.set(FREE);
        return map;
    }

    @Override
    public String toString() {
        return socket.getInetAddress().toString();
    }
}
