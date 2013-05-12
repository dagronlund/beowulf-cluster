package main.programStructure;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author David Gronlund
 */
public class NetworkData {
    
    private static byte KEY_VALUE_START = 1;
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
                int i = (int)(l >> 32);
                os.write(i >> 24);
                os.write(i >> 16);
                os.write(i >> 8);
                os.write(i);
                i = (int)(l);
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
}
