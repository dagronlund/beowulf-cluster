package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.programStructure.Util;

/**
 * @author David Gronlund
 */
public class UserProgramClassLoader extends ClassLoader {
    
    private String classQualifier;

    public UserProgramClassLoader(ClassLoader parent, String classQualifier) {
        super(parent);
        this.classQualifier = classQualifier;
    }

    @Override
    public Class loadClass(String classLoc) throws ClassNotFoundException {
        //Neeeeeed to fix this
        System.out.println(this.classQualifier);
        System.out.println(Util.classLocationToQualifier(classLoc));
        if (!classQualifier.equals(Util.classLocationToQualifier(classLoc))) {
            return super.loadClass(classLoc);
        }
        //
        try {
            File file = new File(classLoc);
            InputStream in = new FileInputStream(file);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            return defineClass(classQualifier, b, 0, b.length);
        } catch (MalformedURLException ex) {
            Logger.getLogger(UserProgramClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserProgramClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
