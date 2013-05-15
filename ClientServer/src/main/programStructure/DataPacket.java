package main.programStructure;

/**
 * @author David Gronlund
 */
public class DataPacket implements Packet {

    private String key;
    private byte[] data;

    public DataPacket(String key, byte[] data) {
        this.key = key;
        this.data = data;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public byte[] getData() {
        return data;
    }
}
