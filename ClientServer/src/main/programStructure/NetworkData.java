package main.programStructure;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David Gronlund
 */
public class NetworkData {
    
    

    private static byte PACKET_START = 1;
    private static byte BYTE_VALUE = 2;
    private static byte SHORT_VALUE = 3;
    private static byte INT_VALUE = 4;
    private static byte LONG_VALUE = 5;
    private static byte FLOAT_VALUE = 6;
    private static byte DOUBLE_VALUE = 7;
    Map<String, Object> data;

    public NetworkData() {
        data = new HashMap<String, Object>();
    }

    public boolean keyExists(String key) {
        return data.containsKey(key);
    }

    public void addByte(String key, byte b) {
        data.put(key, b);
    }

    public void addShort(String key, short s) {
        data.put(key, s);
    }

    public void addInt(String key, int i) {
        data.put(key, i);
    }

    public void addLong(String key, long l) {
        data.put(key, l);
    }

    public void addChar(String key, char c) {
        data.put(key, c);
    }

    public void addFloat(String key, float f) {
        data.put(key, f);
    }

    public void addDouble(String key, double d) {
        data.put(key, d);
    }

    public void addString(String key, String s) {
        data.put(key, s);
    }

    public void addByteArray(String key, byte[] b) {
        data.put(key, b);
    }

    public void addPackableClass(String key, SerializableData p) {
        data.put(key, p);
    }

    public void send(OutputStream os) throws IOException {
        for (String key : data.keySet()) {
            Object o = data.get(key);
            if (o instanceof Byte) {
                os.write((Byte) o);
            } else if (o instanceof Short) {
                short s = (Short) o;
                os.write(s);
                os.write(s >> 8);
            } else if (o instanceof Integer) {
                int i = (Integer) o;
                os.write(i >> 24);
                os.write(i >> 16);
                os.write(i >> 8);
                os.write(i);
            } else if (o instanceof Long) {
                long l = (Long) o;
                int i = (int) (l >> 32);
                os.write(i >> 24);
                os.write(i >> 16);
                os.write(i >> 8);
                os.write(i);
                i = (int) (l);
                os.write(i >> 24);
                os.write(i >> 16);
                os.write(i >> 8);
                os.write(i);
            } else if (o instanceof String) {
                String s = (String) o;
                for (char c : s.toCharArray()) {
                    os.write(c >> 8);
                    os.write(c);
                }
            } else if (o instanceof byte[]) {
                byte[] bytes = (byte[]) o;
                for (byte b : bytes) {
                    os.write(b);
                }
            }
        }
    }

    private void send(OutputStream os, SerializableData data) {
        try {
            os.write(intToBytes(data.getKey().length()));
            for (char c : data.getKey().toCharArray()) {
                os.write(charToBytes(c));
            }
            os.write(intToBytes(data.getData().length));
            os.write(data.getData());
        } catch (IOException ex) {
            Logger.getLogger(NetworkData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void recv(InputStream in) {
        try {
            
            if (in.read() == PACKET_START) { 
                byte[] bytes = new byte[4];
                in.read(bytes);
                int length = bytesToInt(bytes);
                bytes = new byte[length * 2];
                in.read(bytes);
                char[] type = new char[length];
                for (int i = 0; i < length; i++) {
                    type[i] = bytesToChar(new byte[]{bytes[(i * 2)], bytes[(i * 2) + 1]});
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(NetworkData.class.getName()).log(Level.SEVERE, null, ex);
        }
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
