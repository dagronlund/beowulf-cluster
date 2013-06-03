package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import runtime.JarUnpacker;
import main.program.Network;
import main.program.PacketMap;
import runtime.TaskPackage;
import runtime.UserCodeFactory;
import main.program.user.UserProgram;

/**
 *
 * @author abell
 */
public class ServerProgram {

    private Map<Integer, PacketMap> results;
    private boolean done = false;
    private ServerSocket serverSocket;
    private ArrayList<Slave> slaves;
    private UserCodeFactory codeFactory;
    private JarUnpacker jar;
    private Thread listener = new Thread() {
        @Override
        public void run() {
            while (!done) {
                try {
                    Socket sk = serverSocket.accept();
                    addSlave(sk);
                } catch (IOException ex) {
                }
            }
        }
    };

    public ServerProgram() throws IOException {
        serverSocket = new ServerSocket(Network.PORT);
        slaves = new ArrayList<Slave>();
        results = new HashMap<Integer, PacketMap>();
        listener.start();
    }

    public ServerProgram(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        slaves = new ArrayList<Slave>();
        results = new HashMap<Integer, PacketMap>();
        listener.start();
    }

    public void refreshSlaves() {
        for (int i = 0; i < slaves.size(); i++) {
            if (slaves.get(i).getStatus() == Network.OFFLINE) {
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

    public int addTask(String taskId, final PacketMap map) {
        final TaskPackage tp =
                new TaskPackage(taskId, jar.getRawBinary());
        final int id = Network.generateID();
        Thread wait = new Thread() {
            @Override
            public void run() {
                while (!done) {
                    for (Slave s : slaves) {
                        if (s.getStatus() == Network.ONLINE) {
                            PacketMap result = s.runTask(tp, map);
                            results.put(id, result);
                            return;
                        }
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        wait.start();
        return id;
    }

    public PacketMap getTaskResult(int id) {
        if (results.containsKey(id)) {
            return results.get(id);
        }
        return null;
    }

    public void shutdown() throws IOException {
        done = true;
        serverSocket.close();
        for (Slave s : slaves) {
            s.shutdown();
        }
    }

    public void runProgram(String loc) throws FileNotFoundException, IOException {
        jar = new JarUnpacker(new File(loc), "code_base");
        jar.extract();
        codeFactory = new UserCodeFactory(jar.getExtractDirectory());
        UserProgram userProgram = codeFactory.getUserProgram(this);
        userProgram.run();
    }
}
