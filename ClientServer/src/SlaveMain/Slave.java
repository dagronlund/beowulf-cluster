package SlaveMain;

import java.util.ArrayList;
import main.programStructure.Task;

/**
 *
 * @author abell
 */
public class Slave {
    ArrayList<Task> taskList = new ArrayList<Task>();
    Server master;
    public Slave(){
        
    }
    
    public void MASTER(String ad){
        master = new Server(ad);
    }
}
