package main.programStructure;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David Gronlund
 */
public class NetworkPackets {

    public static int TIMEOUT = 200;
    private static byte PACKET_START = 1;
    Map<String, Packet> data;

    public NetworkPackets() {
        data = new HashMap<String, Packet>();
    }

    public void addPacket(Packet p) {
        data.put(p.getKey(), p);
    }

    public Packet getPacket(String key) {
        return data.get(key);
    }

    public boolean packetExists(String key) {
        return data.containsKey(key);
    }

    public void send(OutputStream out) {
        for (String key : data.keySet()) {
            write(out, data.get(key));
        }
    }

    public void receive(InputStream in) {
        Packet p = read(in);
        while (p != null) {
            data.put(p.getKey(), p);
            p = read(in);
        }
    }

    private void write(OutputStream out, Packet data) {
        try {
            out.write(PACKET_START);
            out.write(intToBytes(data.getKey().length()));
            for (char c : data.getKey().toCharArray()) {
                out.write(charToBytes(c));
            }
            out.write(intToBytes(data.getData().length));
            out.write(data.getData());
        } catch (IOException ex) {
            Logger.getLogger(NetworkPackets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Packet read(InputStream in) {
        try {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) <= TIMEOUT) {
                if (in.read() == PACKET_START) {
                    byte[] bytes = new byte[4];
                    in.read(bytes);
                    int length = bytesToInt(bytes);
                    bytes = new byte[length * 2];
                    in.read(bytes);
                    char[] key = new char[length];
                    for (int i = 0; i < length; i++) {
                        key[i] = bytesToChar(new byte[]{bytes[(i * 2)], bytes[(i * 2) + 1]});
                    }
                    bytes = new byte[4];
                    in.read(bytes);
                    length = bytesToInt(bytes);
                    bytes = new byte[length];
                    in.read(bytes);
                    return new DataPacket(new String(key), bytes);
                }
            }
        } catch (SocketException ex) {
            return null;
        } catch (IOException ex) {
            Logger.getLogger(NetworkPackets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static byte[] intToBytes(int i) {
        return new byte[]{(byte) (i >> 24), (byte) (i >> 16), (byte) (i >> 8), (byte) (i)};
    }

    private static int bytesToInt(byte[] b) {
        return (b[0] << 24) | (b[1] << 16) | (b[2] << 8) | b[3];
    }

    private static byte[] charToBytes(char c) {
        return new byte[]{(byte) (c >> 8), (byte) (c)};
    }

    private static char bytesToChar(byte[] b) {
        return (char) ((b[0] << 8) | b[1]);
    }
}
