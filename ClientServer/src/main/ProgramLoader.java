package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author dgronlund
 */
public class ProgramLoader {

    private File directory;
    private String mainProgram;
    private Map<String, String> tasks;

    public ProgramLoader(String url) throws FileNotFoundException {
        directory = new File(url);
        tasks = new HashMap<String, String>();
        readRegistry();
    }
    
    public String getMainProgram() {
        return mainProgram.replace(".", "/") + ".class";
    }

    public String getTask(String taskID) {
        if (tasks.containsKey(taskID)) {
            return tasks.get(taskID).replace(".", "/") + ".class";
        }
        return null;
    }

    private void readRegistry() throws FileNotFoundException {
        FileReader reader = new FileReader(directory.toString() + "/registry.dat");
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
