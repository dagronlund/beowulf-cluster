package main.programStructure;

/**
 * @author David Gronlund
 */
public interface Packet {
    
    public int TYPE_BYTE_ARRAY = 1;
    
    public String getKey();
    
    public byte[] getData();
    
}
