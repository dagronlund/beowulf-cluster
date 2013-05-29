package main.control;

import java.io.IOException;
import server.ServerProgram;
import slave.SlaveProgram;

/**
 *
 * @author abell
 */
public class MainInterface {

    public static UserInterface server(int port) throws IOException {
        ServerProgram prog;
        if (port == -1) {
            prog = new ServerProgram();
        } else {
            prog = new ServerProgram(port);
        }
        return new ServerInterface(prog);
    }

    public static UserInterface slave(String ip, int port) throws IOException {
        SlaveProgram prog;
        if (port == -1) {
            prog = new SlaveProgram(ip);
        } else {
            prog = new SlaveProgram(ip, port);
        }
        return new SlaveInterface(prog);
    }

    public static void main(String[] args) {
        UserInterface userInterface = null;
        while (userInterface == null) {
            if (args == null || args.length == 0) {
                System.out.println("This assumes 'slave localhost:1234'");
                System.out.println("If you don't want this, exit and use 'help'");
            } else if (args[0].toLowerCase().equals("slave")) {
                if (args.length >= 2 && args[1].contains(":")) {
                    String ip = args[1].split(":")[0];
                    String port = args[1].split(":")[1];
                    try {
                        userInterface = slave(ip, Integer.parseInt(port.trim()));
                    } catch (IOException ex) {
                        System.out.println("Slave program failed due to fatal error.");
                    }
                }
            } else if (args[0].toLowerCase().equals("server")) {
                if (args.length >= 2) {
                    try {
                        userInterface = server(Integer.parseInt(args[1].trim()));
                    } catch (IOException ex) {
                        System.out.println("Server program failed due to fatal error.");
                    }
                }
            } else if (args[0].toLowerCase().equals("help")) {
                System.out.println("Valid argument structure is:");
                System.out.println("[slave|server] ip:port");
                System.out.println("To use the default port, use -1 as the port argument.");
            } else {
                System.out.println("Invaid command. Please type 'help' for assistance.");
            }
        }
        userInterface.run();
        System.out.println("Program Complete. G'Day");
    }
}
