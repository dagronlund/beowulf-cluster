package main.program;

import runtime.TaskPackage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dgronlund
 */
public class Network {

    public static final int NETWORK_WAIT = 1000;
    public static final int PORT = 1234;
    public static final byte HANDSHAKE = 42;
    public static final byte END_DATA = -1;
    public static final byte HEARTBEAT = 0;
    public static final byte PACKET_START = 1;
    public static final byte TASK_START = 2;
    private static int id = 0;

    public static int generateID() {
        return id++;
    }

    public static boolean handshake(Socket sck) throws IOException {
        try {
            sck.getOutputStream().write(HANDSHAKE);
        } catch (SocketException ex) {
        }
        long start = System.currentTimeMillis();
        while ((System.currentTimeMillis() - start) <= NETWORK_WAIT) {
            if (sck.getInputStream().available() > 0
                    && sck.getInputStream().read() == HANDSHAKE) {
                return true;
            }
        }
        return false;
    }

    public static PacketMap executeTask(Socket sck, TaskPackage task, PacketMap packets) throws IOException {
        OutputStream out = sck.getOutputStream();
        writeString(out, task.getTaskId());
        System.out.println("Task data size: " + task.getJarData().length);
        writeData(out, task.getJarData());
        if (packets != null) {
            writeInt(out, 1);
            packets.send(out);
        } else {
            writeInt(out, 0);
        }
        PacketMap map = new PacketMap();
        map.receive(sck.getInputStream());
        return map;
    }

    public static void writeInt(OutputStream out, int i) {
        try {
            out.write(intToBytes(i));
        } catch (IOException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int readInt(InputStream in) {
        try {
            byte[] bytes = new byte[4];
            in.read(bytes);
            return bytesToInt(bytes);
        } catch (IOException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static void writeString(OutputStream out, String s) {
        try {
            writeInt(out, s.length() * 2);
            for (int i = 0; i < s.length(); i++) {
                out.write(charToBytes(s.charAt(i)));
            }
        } catch (IOException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String readString(InputStream in) {
        try {
            int size = readInt(in);
            waitUntilAvailable(in, size);
            byte[] bytes = new byte[size];
            in.read(bytes);
            char[] s = new char[size / 2];
            for (int i = 0; i < size / 2; i++) {
                s[i] = bytesToChar(new byte[]{bytes[(i * 2)], bytes[(i * 2) + 1]});
            }
            return new String(s);
        } catch (IOException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void writeData(OutputStream out, byte[] data) {
        try {
            writeInt(out, data.length);
            out.write(data);
        } catch (IOException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static byte[] readData(InputStream in) {
        try {
            int length = readInt(in);
            waitUntilAvailable(in, length);
            byte[] data = new byte[length];
            in.read(data);
            return data;
        } catch (IOException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static byte[] intToBytes(int i) {
        return new byte[]{(byte) (i >> 24), (byte) (i >> 16), (byte) (i >> 8), (byte) (i)};
    }

    private static int bytesToInt(byte[] b) {
        return ((b[0] & 0xFF) << 24) | ((b[1] & 0xFF) << 16) | ((b[2] & 0xFF) << 8) | (b[3] & 0xFF);
    }

    private static byte[] charToBytes(char c) {
        return new byte[]{(byte) (c >> 8), (byte) (c)};
    }

    private static char bytesToChar(byte[] b) {
        return (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
    }

    public static boolean timedOut(long start) {
        return (System.currentTimeMillis() - start) > NETWORK_WAIT;
    }
    
    public static void waitUntilAvailable(InputStream in, int numBytes) throws IOException {
        while (in.available() < numBytes);
    }
}
