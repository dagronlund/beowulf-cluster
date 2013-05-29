package main.control;

import slave.SlaveProgram;

/**
 * @author David Gronlund
 */
public class SlaveInterface extends UserInterface {

    private SlaveProgram program;

    public SlaveInterface(SlaveProgram program) {
        this.program = program;
    }

    @Override
    public void run() {
    }
}
