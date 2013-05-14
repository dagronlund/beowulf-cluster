package server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import main.programStructure.Network;
import main.programStructure.Task;

/**
 *
 * @author abell
 */
public class Slave {

    private ArrayList<Task> taskList = new ArrayList<Task>();
    private Socket socket;
    private String address;
    private int id;

    public Slave(Socket socket, int id) throws IOException {
        this.socket = socket;
        this.id = id;
        address = socket.getInetAddress().getHostAddress();
        if (socket.getInputStream().read() != Network.HANDSHAKE) {
            throw new IOException();
        }
        socket.getOutputStream().write(Network.HANDSHAKE);
        byte[] idBytes = new byte[4];
        idBytes[0] = (byte) (id >> 24);
        idBytes[1] = (byte) (id >> 16);
        idBytes[2] = (byte) (id >> 8);
        idBytes[3] = (byte) (id);
        socket.getOutputStream().write(idBytes);
    }

    public String getAddress() {
        return address;
    }

    public Socket getConnection() {
        return socket;
    }

    public int getID() {
        return id;
    }

    public void updateTasks() {
        //TODO: Send a packet to Slaves to ask for tasklist, make taskList equal to return.
    }

    public int getAmountOfTasks() {
        int ret = 0;
        for (int i = 0; i < taskList.size(); i++) {
            ret++;
        }
        return ret;
    }
    
    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
