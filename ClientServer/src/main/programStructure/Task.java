package main.programStructure;

/**
 * @author David Gronlund
 */
public class Task {

    private int taskID;
    private byte[] jarData;
    private String classLocation;
    private PacketMap map;

    /**
     * Primary constructor for the Task class, takes the code and name.
     * @param jarData This is the raw binary data that comprises the class to
     * run.
     * @param classLocation This is the fully qualified name of the class to run
     * (i.e. main/code/Main.class) where the packages are separated by / and the
     * string is terminated by .class. This should be returned by getMainProgram
     * or getTask in UserProgramLoader.
     */
    public Task(int taskID, String classLocation,  byte[] jarData) {
        this.taskID = taskID;
        this.jarData = jarData;
        this.classLocation = classLocation;
    }
    
    public void setPacketMap(PacketMap map) {
        this.map = map;
    }
    
    public PacketMap getPacketMap() {
        return map;
    }
    
    public int getTaskID() {
        return taskID;
    }
    
    public byte[] getJarData() {
        return jarData;
    }
    
    public String getClassLocation() {
        return classLocation;
    }
}
