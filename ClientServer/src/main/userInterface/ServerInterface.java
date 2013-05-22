package main.userInterface;

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
        System.out.print("Enter the location of the jar file to execute: ");
        String loc = getInput().nextLine();
        program.kill();
    }
}
