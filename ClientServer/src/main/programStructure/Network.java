package main.programStructure;

/**
 *
 * @author dgronlund
 */
public class Network {

    public static final int PORT = 1234;
    public static final byte HANDSHAKE = 42;
    private static int id = 0;

    public static int generateID() {
        return id++;
    }
}
