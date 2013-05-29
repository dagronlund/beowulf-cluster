package runtime;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import main.program.user.UserProgram;
import main.program.user.UserTask;
import server.ServerProgram;

/**
 *
 * @author David Gronlund
 */
public class UserCodeFactory {

    private ProgramLoader loader;

    /**
     * Creates a new UserCodeFactory using the specified directory as the root.
     * A program loader is created in order to decipher the registry found in
     * the program.
     * 
     * @param directory The location of the code to load
     * @throws FileNotFoundException 
     */
    public UserCodeFactory(File directory) throws FileNotFoundException {
        loader = new ProgramLoader(directory);
    }

    /**
     * Returns an instance of the UserProgram specified in the registry.
     * 
     * @param server The ServerProgram for the UserProgram to reference
     * @return An instance of UserProgram as specified in the registry
     */
    public UserProgram getUserProgram(ServerProgram server) {
        UserClassLoader classLoader = new UserClassLoader(
                getClass().getClassLoader(),
                loader.getMainProgram());
        UserProgram program = null;
        try {
            Class userProgramClass = classLoader.loadClass(
                    loader.getPath().toString()
                    + Util.qualifierToClassLocation(loader.getMainProgram()));
            program = (UserProgram) userProgramClass.
                    getConstructor(server.getClass()).newInstance(server);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return program;
    }

    /**
     * Returns an instance of the specified task.
     * 
     * @param id The id of the task to create
     * @return An instance of the specified task
     */
    public UserTask getUserTask(String id) {
        UserClassLoader classLoader = new UserClassLoader(
                getClass().getClassLoader(), loader.getTask(id));
        UserTask task = null;
        try {
            Class userProgramClass = classLoader.loadClass(
                    loader.getPath().toString()
                    + Util.qualifierToClassLocation(loader.getTask(id)));
            task = (UserTask) userProgramClass.newInstance();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return task;
    }

    /**
     * Returns the ProgramLoader used to load this program.
     * 
     * @return The ProgramLoader for this program 
     */
    public ProgramLoader getLoader() {
        return loader;
    }
}
