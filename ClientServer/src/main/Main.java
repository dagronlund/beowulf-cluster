package main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.programStructure.Task;
import main.programStructure.UserProgram;

/**
 * @author David Gronlund
 */
public class Main {

    public static void main(String[] args) throws UnknownHostException, IOException {
        UserProgramClassLoader loader = new UserProgramClassLoader(Main.class.getClassLoader(),
                "test.otherTest.ProgramTest");
        UserProgram user = null;
        JarUnpacker p = new JarUnpacker(
                new File("../UserProgram/dist/UserProgram.jar"), "code_temp");
        p.extract();
        try {
            Class userProgramClass = loader.loadClass(p.getExtractDirectory() + "/test/otherTest/"
                    + "ProgramTest.class");
            user = (UserProgram) userProgramClass.getConstructor(Main.class).newInstance(new Main());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        user.run();
    }

    public Map<String, Object> submitTask(Task task) {
        return null;
    }
}
