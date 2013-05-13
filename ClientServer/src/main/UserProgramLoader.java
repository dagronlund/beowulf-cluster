package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author David Gronlund
 */
public class UserProgramLoader {

    ZipInputStream jarFile;
    private String registry;

    public UserProgramLoader(String location) throws FileNotFoundException, IOException {
        File file = new File(location);
        //FileInputStream in = new FileInputStream(file);
        //jarFile = new ZipInputStream(in);
        //in.reset();
//        ZipEntry entry;
//        while ((entry = jarFile.getNextEntry()) != null) {
//            if (entry.getName().equals("registry.dat")) {
//                System.out.println("I found it, sucka!");
//                byte[] data = new byte[(int) entry.getSize()];
//                jarFile.read(data);
//                registry = new String(data);
//            }
//        }
        findFile("registry.dat", file);
        //findFile("", file);
    }
    
    private byte[] findFile (String location, File f) throws IOException {
        ZipInputStream file = new ZipInputStream(new FileInputStream(f));
        ZipEntry entry;
        while ((entry = file.getNextEntry()) != null) {
            if (entry.getName().equals(location)) {
                byte[] data = new byte[(int)entry.getSize()];
                file.read(data);
                return data;
            }
        }
        return null;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        UserProgramLoader temp = new UserProgramLoader("../UserProgram/dist/UserProgram.jar");
    }
}
