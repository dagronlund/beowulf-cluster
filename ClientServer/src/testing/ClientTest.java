package testing;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David Gronlund
 */
public class ClientTest {

    public static final int PORT = 1235;

    public static void main(String[] args) {
        System.out.println("Client Running");
        try {
            Socket sk = new Socket(InetAddress.getByName("localhost"), PORT);
            Scanner in = new Scanner(sk.getInputStream());
            PrintWriter out = new PrintWriter(sk.getOutputStream(), true);

            String s = new Scanner(System.in).nextLine();
            out.println(s);
            System.out.println(in.nextLine());
            System.out.println("Client Success");
            sk.close();
        } catch (ConnectException ce) {
            System.out.println("No server found at that ip.");
        } catch (IOException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
