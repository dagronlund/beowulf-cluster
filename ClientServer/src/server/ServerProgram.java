package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.programStructure.Network;

/**
 *
 * @author abell
 */
public class ServerProgram {

    private ServerSocket serverSocket;
    public ArrayList<Slave> slaves;
    private Thread listener = new Thread() {
        @Override
        public void run() {
            while (true) {
                refreshSlaves();
                try {
                    Socket sk = serverSocket.accept();
                    System.out.println("Slave found");
                    addSlave(sk);
                } catch (IOException ex) {
                    Logger.getLogger(ServerProgram.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    public ServerProgram() throws IOException {
        serverSocket = new ServerSocket(Network.PORT);
        slaves = new ArrayList<Slave>();
        listener.start();
    }
    
    private void refreshSlaves() {
        for (int i = 0; i < slaves.size(); i++) {
            if (slaves.get(i).getConnection().isClosed()) {
                slaves.remove(i);
                System.out.println("slave lost");
                i--;
            } else {
                System.out.println("slave operating");
            }
        }
    }

    private void addSlave(Socket connection) {
        try {
            slaves.add(new Slave(connection, Network.generateID()));
        } catch (IOException ex) {
            Logger.getLogger(ServerProgram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Slave getSlave(int id) {
        for (Slave s : slaves) {
            if (s.getID() == id) {
                return s;
            }
        }
        return null;
    }
    
    public static void main(String[] args) throws IOException {
        ServerProgram program = new ServerProgram();
        System.out.println("Here");
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerProgram.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
