package main.userInterface;

import java.util.Scanner;

/**
 * @author David Gronlund
 */
public abstract class UserInterface {

    private Scanner s;
    
    public UserInterface() {
        s = new Scanner(System.in);
    }
    
    public Scanner getInput() {
        return s;
    }
    
    public abstract void run();
}
