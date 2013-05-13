package main.programStructure;

/**
 *
 * @author abell
 */
public class ServerToSlaveAdministration {
    /*
     * 
     */
    ServerMain.Server BigBrother = new ServerMain.Server();
    
    public String getSlaves(){
        return BigBrother.returnSlaves();
    }
    
    public String getSlave(ServerMain.Slave s){
        return BigBrother.returnSlave(s);
    }
}
