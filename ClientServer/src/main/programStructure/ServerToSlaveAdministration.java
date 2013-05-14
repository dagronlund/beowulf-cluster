package main.programStructure;

/**
 *
 * @author abell
 */
public class ServerToSlaveAdministration {
    /*
     * 
     */
    server.ServerProgram BigBrother = new server.ServerProgram();
    
    public String getSlaves(){
        return BigBrother.returnSlaves();
    }
    
    public String getSlave(server.Slave s){
        return BigBrother.returnSlave(s);
    }
}
