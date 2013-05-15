package test.otherTest;

import test.*;
import main.Main;
import main.programStructure.UserProgram;

/**
 * @author David Gronlund
 */
public class ProgramTest extends UserProgram {
    
    public ProgramTest(Main main) {
        super(main);
    }

    @Override
    public void run() {
        System.out.println("This works");
    }
}
