package main;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author David Gronlund
 */
public class UserProgramLoader {

    private byte[] file;
    private String mainProgram;
    private Map<String, String> tasks;

    public UserProgramLoader(String location)
            throws FileNotFoundException, IOException {
        File temp = new File(location);
        InputStream stream = new FileInputStream(temp);
        file = new byte[stream.available()];
        stream.read(file);
        tasks = new HashMap<String, String>();
        byte[] reg = findFile("registry.dat");
        readRegistry(new String(reg));
    }
    
    public UserProgramLoader(byte[] file)
            throws FileNotFoundException, IOException {
        this.file = file;
        tasks = new HashMap<String, String>();
        byte[] reg = findFile("registry.dat");
        readRegistry(new String(reg));
    }

    private void readRegistry(String registry) {
        Scanner scan = new Scanner(registry);
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

    public String getMainProgram() {
        return mainProgram.replace(".", "/") + ".class";
    }

    public String getTask(String taskID) {
        if (tasks.containsKey(taskID)) {
            return tasks.get(taskID).replace(".", "/") + ".class";
        }
        return null;
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

    public final byte[] findFile(String location) throws IOException {
        ZipInputStream zipFile = new ZipInputStream(new ByteArrayInputStream(file));
        ZipEntry entry;
        while ((entry = zipFile.getNextEntry()) != null) {
            if (entry.getName().equals(location)) {
                byte[] data = new byte[(int) entry.getSize()];
                zipFile.read(data);
                return data;
            }
        }
        return null;
    }

    public static void main(String[] args)
            throws FileNotFoundException, IOException {
        UserProgramLoader temp =
                new UserProgramLoader("../UserProgram/dist/UserProgram.jar");
    }
}
