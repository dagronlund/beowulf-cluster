package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.JarUnpacker;
import main.Main;
import main.ProgramLoader;
import main.UserClassLoader;
import main.programStructure.Network;
import main.programStructure.UserProgram;
import main.programStructure.Util;

/**
 *
 * @author abell
 */
public class ServerProgram {

    private boolean done = false;
    private ServerSocket serverSocket;
    public ArrayList<Slave> slaves;
    private Thread listener = new Thread() {
        @Override
        public void run() {
            while (!done) {
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

    public void runProgram(String loc) throws FileNotFoundException, IOException {
        JarUnpacker jar = new JarUnpacker(new File(loc), "code_base");
        jar.extract();
        ProgramLoader loader = new ProgramLoader(jar.getExtractDirectory().toString());
        UserClassLoader classLoader = new UserClassLoader(
                getClass().getClassLoader(), 
                Util.classLocationToQualifier(loader.getMainProgram()));
        UserProgram user = null;
        try {
            Class userProgramClass = 
                    classLoader.loadClass(
                    jar.getExtractDirectory() + "/" + loader.getMainProgram());
            user = (UserProgram) userProgramClass.getConstructor(getClass()).newInstance(this);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        user.run();
    }
}
