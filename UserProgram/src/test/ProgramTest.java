package test;

import main.program.PacketMap;
import main.program.user.UserProgram;
import server.ServerProgram;

/**
 * @author David Gronlund
 */
public class ProgramTest extends UserProgram {

    public ProgramTest(ServerProgram server) {
        super(server);
    }

    @Override
    public void run() {
        System.out.println();
        int id = getServer().addTask("something", null);
        while (getServer().getTaskResult(id) == null) {
        }
        PacketMap map = getServer().getTaskResult(id);
        System.out.println("Packet recv: " + map.getPacket("test").getData()[0]);
        System.out.println("Success for user program.");
        System.out.println();
    }
}
