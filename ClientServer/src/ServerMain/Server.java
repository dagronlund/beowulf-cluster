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
    
    public int getIndexOfSlave(Slave s){
        int ret = -1;
        for(int i = 0; i<slaves.size(); i++){
            if(slaves.get(i).equals(s))
                ret = i;
        }
        return ret;
    }
    
    public String returnSlave(Slave s){
        int ind = getIndexOfSlave(s);
        return slaves.get(ind).getHostName() + "  at  " + slaves.get(ind).getAddress() 
                    + "  with  " + slaves.get(ind).getAmountOfTasks() + " tasks.";
    }
}
