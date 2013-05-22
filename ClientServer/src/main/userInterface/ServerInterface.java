package main.userInterface;

import java.io.File;
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
            } else if (s.toLowerCase().equals("execute")) {
                System.out.println("Jar file to run: ");
                s = getInput().nextLine();
                try {
                    program.runProgram(s);
                } catch (FileNotFoundException ex) {
                    System.out.println("That is not a valid file location.");
                } catch (IOException ex) {
                    System.out.println("Fatal io exception.");
                }
            }
        }
        System.out.println("Server Done");
    }
}
