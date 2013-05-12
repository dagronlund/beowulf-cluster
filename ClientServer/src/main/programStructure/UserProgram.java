package main.programStructure;

import main.Main;

/**
 * @author David Gronlund
 */
public abstract class UserProgram {
    
    private Main main;
    
    public UserProgram(Main main) {
        this.main = main;
    }
    
    public Main getMainProgram() {
        return main;
    }
    
    public abstract void run();
}
