package main.programStructure;

import server.ServerProgram;

/**
 * @author David Gronlund
 */
public abstract class UserProgram {

    private ServerProgram program;

    public UserProgram(ServerProgram program) {
        this.program = program;
    }

    public ServerProgram getServer() {
        return program;
    }

    public abstract void run();
}
