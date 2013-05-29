package runtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David Gronlund
 */
public class UserClassLoader extends ClassLoader {

    private String classQualifier;

    /**
     * Constructs a custom class loader using any available ClassLoader and the
     * qualifier of the class it is supposed to load.
     *
     * @param parent The class loader of which to use
     * @param classQualifier The qualifier of the class to load
     */
    public UserClassLoader(ClassLoader parent, String classQualifier) {
        super(parent);
        this.classQualifier = classQualifier;
    }

    /**
     * Loads the class for this class loader given its location.
     *
     * @param classLoc The location of the class to load
     * @return An instance of the Class object for this class
     * @throws ClassNotFoundException
     */
    @Override
    public Class loadClass(String classLoc) throws ClassNotFoundException {
        if (!classLoc.endsWith(Util.qualifierToClassLocation(classQualifier))) {
            return super.loadClass(classLoc);
        }
        try {
            File file = new File(classLoc);
            InputStream in = new FileInputStream(file);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            return defineClass(classQualifier, b, 0, b.length);
        } catch (MalformedURLException ex) {
            Logger.getLogger(UserClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
