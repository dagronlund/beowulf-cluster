package main.program;

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
public class PacketMap {

    Map<String, Packet> data;

    public PacketMap() {
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
        Network.writeInt(out, data.size());
        for (String key : data.keySet()) {
            write(out, data.get(key));
        }
    }

    public void receive(InputStream in) {
        int size = Network.readInt(in);
        for (int i = 0; i < size; i++) {
            Packet p = read(in);
            data.put(p.getKey(), p);
        }
    }

    private void write(OutputStream out, Packet data) {
        try {
            out.write(Network.PACKET_START);
            Network.writeString(out, data.getKey());
            Network.writeData(out, data.getData());
        } catch (IOException ex) {
            Logger.getLogger(PacketMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Packet read(InputStream in) {
        try {
            while (true) {
                if (in.read() == Network.PACKET_START) {
                    String key = Network.readString(in);
                    byte[] packetData = Network.readData(in);
                    return new DataPacket(key, packetData);
                }
            }
        } catch (SocketException ex) {
            return null;
        } catch (IOException ex) {
            Logger.getLogger(PacketMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
