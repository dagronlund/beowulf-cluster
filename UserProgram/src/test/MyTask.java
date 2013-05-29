package test;

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
        return new PacketMap();
    }    
}
