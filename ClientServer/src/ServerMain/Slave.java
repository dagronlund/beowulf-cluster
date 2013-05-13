package ServerMain;

import java.util.ArrayList;
import main.programStructure.Task;

/**
 *
 * @author abell
 */
public class Slave {
    ArrayList<Task> taskList = new ArrayList<Task>();
    private String address;
    private String name;
    
    public Slave(String nom, String ad){
        address = ad;
        name = nom;
    }
    
    public String getHostName(){
        return name;
    }
    
    public String getAddress(){
        return address;
    }
    
    public void updateTasks(){
        //TODO: Send a packet to Slaves to ask for tasklist, make taskList equal to return.
    }
    public int getAmountOfTasks(){
        int ret = 0;
        for(int i = 0; i<taskList.size(); i++){
            ret++;
        }
        return ret;
    }
}
