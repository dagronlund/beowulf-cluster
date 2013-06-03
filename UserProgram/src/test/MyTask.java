package test;

import java.util.logging.Level;
import java.util.logging.Logger;
import main.program.DataPacket;
import main.program.PacketMap;
import main.program.user.UserTask;

/**
 *
 * @author dgronlund
 */
public class MyTask extends UserTask {

    @Override
    public PacketMap run(PacketMap map) {
        System.out.println("Ow, jerkface!");
        PacketMap result = new PacketMap();
        result.addPacket(new DataPacket("test", new byte[]{-42, 1, 2, 3}));
        return result;
    }
}
