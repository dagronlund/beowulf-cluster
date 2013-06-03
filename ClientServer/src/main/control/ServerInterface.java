package main.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ServerProgram;

/**
 * @author David Gronlund
 */
public class ServerInterface extends UserInterface {

    private ServerProgram program;

    public ServerInterface(ServerProgram program) {
        this.program = program;
    }

    @Override
    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.print("Command: ");
            String s = getInput().nextLine();
            if (s.toLowerCase().equals("exit")) {
                exit = true;
                try {
                    program.shutdown();
                } catch (IOException ex) {
                    System.out.println("Program failed to close due to IOException: ");
                    exit = false;
                }
            } else if (s.toLowerCase().equals("execute")) {
                System.out.println("Jar file to run: ");
                s = getInput().nextLine();
                try {
                    program.runProgram(s);
                } catch (FileNotFoundException ex) {
                    System.out.println("That is not a valid file location.");
                } catch (IOException ex) {
                    System.out.println("Fatal IO exception.");
                }
            } else if (s.toLowerCase().equals("execute test")) {
                s = "../UserProgram/dist/UserProgram.jar";
                try {
                    program.runProgram(s);
                } catch (FileNotFoundException ex) {
                    System.out.println("That is not a valid file location.");
                } catch (IOException ex) {
                    System.out.println("Fatal IO exception.");
                }
            }
        }
    }
}
