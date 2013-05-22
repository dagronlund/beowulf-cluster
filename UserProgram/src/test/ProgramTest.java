package test;

import java.awt.Toolkit;
import main.programStructure.UserProgram;
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
        System.out.println("User Program Starting");
        
        System.out.println("Program Done");
        Toolkit.getDefaultToolkit().beep();
    }
}
