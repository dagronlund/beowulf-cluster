package test;

import java.awt.Toolkit;
import main.program.user.UserProgram;
import server.ServerProgram;

/**
 * @author David Gronlund
 */
public class ProgramTest extends UserProgram {

    public ProgramTest(ServerProgram server) {
        super(server);
    }

    @Override
    public void run() {
        getServer().addTask("something", null);
        System.out.println("Success.");
    }
}
