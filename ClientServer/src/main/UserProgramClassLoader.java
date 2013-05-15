package main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David Gronlund
 */
public class UserProgramClassLoader extends ClassLoader {

    public UserProgramClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class loadClass(String className) throws ClassNotFoundException {
        System.out.println(className);
        if (!className.endsWith("ProgramTest.class")) {
            return super.loadClass(className);
        }
        try {
            File file = new File(className);
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            int data = in.read();
            while (data != -1) {
                b.write(data);
                data = in.read();
            }
            in.close();
            byte[] classData = b.toByteArray();
            return defineClass("test.otherTest.ProgramTest", classData, 0, classData.length);
        } catch (MalformedURLException ex) {
            Logger.getLogger(UserProgramClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserProgramClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
