package runtime;

import main.program.PacketMap;

/**
 * @author David Gronlund
 */
public class TaskPackage {

    private byte[] jarData;
    private String taskId;
    private PacketMap map;

    /**
     * Creates a new TaskPackage with the specified data and name.
     *
     * @param jarData The raw binary of the jar file
     * @param taskId The id of the task to run (specified in registry)
     */
    public TaskPackage(String taskId, byte[] jarData) {
        this.jarData = jarData;
        this.taskId = taskId;
    }

    /**
     * Sets the PacketMap to accompany the outgoing task code.
     *
     * @param map The accompanying PacketMap
     */
    public void setPacketMap(PacketMap map) {
        this.map = map;
    }

    /**
     * Returns the accompanying PacketMap for this code.
     *
     * @return map
     */
    public PacketMap getPacketMap() {
        return map;
    }

    /**
     * Returns the raw binary data that composes this jar file.
     *
     * @return jarData
     */
    public byte[] getJarData() {
        return jarData;
    }

    /**
     * Returns the id of the task to execute.
     *
     * @return taskId
     */
    public String getTaskId() {
        return taskId;
    }
}
