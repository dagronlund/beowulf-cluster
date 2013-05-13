package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.jar.JarInputStream;

/**
 * @author David Gronlund
 */
public class UserProgramLoader {

    JarInputStream jarFile;

    public UserProgramLoader(String location) throws FileNotFoundException {
        File file = new File(location);
        InputStream in = new FileInputStream(file);
        jarFile = new JarInputStream(in);
    }
}
