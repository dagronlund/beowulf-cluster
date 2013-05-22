package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.programStructure.Network;
import main.programStructure.Task;

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
                try {
                    Socket sk = serverSocket.accept();
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
    
    public ServerProgram(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        slaves = new ArrayList<Slave>();
        listener.start();
    }

    public void refreshSlaves() {
        for (int i = 0; i < slaves.size(); i++) {
            if (!(slaves.get(i).getState() == Slave.READY)
                    && !(slaves.get(i).getState() == Slave.BUSY)) {
                slaves.remove(i);
                i--;
            }
        }
    }

    private void addSlave(Socket connection) {
        try {
            slaves.add(new Slave(connection));
        } catch (IOException ex) {
            Logger.getLogger(ServerProgram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws IOException {
        ServerProgram program = new ServerProgram();
//        while (program.slaves.size() <= 0) {
//        }
        
        //Task temp = new Task(0, );
        //program.slaves.get(0).runTask(null, null) //program.refreshSlaves();
                //        String s = new Scanner(System.in).nextLine();
                //        if (s.contains("call")) {
                //            if (program.slaves.size() > 0) {
                //            }
                //        }
    }
}
