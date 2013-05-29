package runtime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author David Gronlund
 */
public class ProgramLoader {

    private File path;
    private String mainProgram;
    private Map<String, String> tasks;

    /**
     * Creates a new program loader from the contents of path. This constructor
     * only reads the contents of the registry and stores them.
     *
     * @param path The directory from which to load the program.
     * @throws FileNotFoundException
     */
    public ProgramLoader(File path) throws FileNotFoundException {
        this.path = path;
        tasks = new HashMap<String, String>();
        readRegistry();
    }

    /**
     * Returns the location of the loaded program.
     *
     * @return path
     */
    public File getPath() {
        return path;
    }

    /**
     * Returns the class qualifier of the main program. (e.g.
     * com.david.program.main.MainProgram)
     *
     * @return mainProgram
     */
    public String getMainProgram() {
        return mainProgram;
    }

    /**
     * Return the task qualifier for the specified taskId. (e.g.
     * com.david.program.tasks.RandomTask)
     *
     * @param taskID The task to search for, specified in the registry.
     * @return
     */
    public String getTask(String taskID) {
        if (tasks.containsKey(taskID)) {
            return tasks.get(taskID).replace(".", "/") + ".class";
        }
        return null;
    }

    /**
     * Reads and stores the contents of the registry for this program. This
     * reads the location of the main program and the specific tasks specified
     * in the registry and records them for later use.
     *
     * @throws FileNotFoundException
     */
    private void readRegistry() throws FileNotFoundException {
        FileReader reader = new FileReader(path.toString() + "/registry.dat");
        Scanner scan = new Scanner(reader);
        while (scan.hasNextLine()) {
            String line = cleanString(scan.nextLine());
            if (line.startsWith("program.mainProgram")) {
                mainProgram = line.replaceAll("program.mainProgram=", "").
                        replace("\"", "");
            } else if (line.startsWith("program.task.")) {
                tasks.put(line.replaceFirst("program.task.", "").
                        substring(0, line.replaceFirst("program.task.", "").
                        indexOf("=")),
                        line.substring(line.indexOf("=") + 1).
                        replace("\"", ""));
            }
        }
    }

    private String cleanString(String s) {
        s = s.trim();
        boolean inQuote = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '"') {
                inQuote = !inQuote;
            } else if (!inQuote && s.charAt(i) == ' ') {
                s = s.substring(0, i) + s.substring(i + 1);
                i--;
            }
        }
        return s;
    }
}
