package ServerMain;

import java.util.ArrayList;

/**
 *
 * @author abell
 */
public class Server {
    public ArrayList<Slave> slaves;
    public Server(){
        
    }
    
    public void addSlave(String name, String ad){
        slaves.add(new Slave(name, ad));
    }
    
    public String returnSlaves(){
        String ret = "";
        for(int i = 0; i<slaves.size(); i++){
            slaves.get(i).updateTasks();
            ret += slaves.get(i).getHostName() + "  at  " + slaves.get(i).getAddress() 
                    + "  with  " + slaves.get(i).getAmountOfTasks() + " tasks." + "\n";
        }
        return ret;
    }
}
