package main;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * An object that extracts the contents of a jar file into a specific folder.
 *
 * @author David
 */
public class JarUnpacker {

    private byte[] jarFile;
    private File extractDirectory;

    /**
     * Creates a JarUnpacker using the raw binary of the jar file.
     *
     * @param file The raw data of the jar file to unpack.
     * @param name The sub-directory of the temp directory of which to unpack
     * in.
     */
    public JarUnpacker(byte[] file, String name) {
        jarFile = file;
        extractDirectory =
                new File(System.getProperty("java.io.tmpdir") + name);
    }

    /**
     * Creates a JarUnpacker using the file location of the jar file.
     *
     * @param file The location of the jar file to unpack.
     * @param name The sub-directory of the temp directory of which to unpack
     * in.
     */
    public JarUnpacker(File file, String name)
            throws FileNotFoundException, IOException {
        InputStream in = new FileInputStream(file);
        jarFile = new byte[in.available()];
        in.read(jarFile);
        extractDirectory =
                new File(System.getProperty("java.io.tmpdir") + name);
    }

    /**
     * Returns the directory in which the jar file will be extracted.
     *
     * @return extractDirectory
     */
    public File getExtractDirectory() {
        return extractDirectory;
    }

    /**
     * Extracts the files in the specified jar file into the specified
     * directory. This deletes that directory and all of its contents before it
     * runs and copies all files and folders as is.
     *
     * @throws IOException
     */
    public void extract() throws IOException {
        if (extractDirectory.exists()) {
            deleteDir(extractDirectory);
        }
        extractDirectory.mkdirs();
        ZipInputStream zipFile =
                new ZipInputStream(new ByteArrayInputStream(jarFile));
        ZipEntry entry;
        while ((entry = zipFile.getNextEntry()) != null) {
            byte[] data = new byte[(int) entry.getSize()];
            zipFile.read(data);
            if (entry.isDirectory()) {
                new File(extractDirectory.toString() + "\\" + entry).mkdir();
            } else {
                File f = new File(extractDirectory.toString() + "\\" + entry);
                f.createNewFile();
                OutputStream out = new FileOutputStream(f);
                out.write(data);
                out.close();
            }
        }
    }

    /**
     * Deletes the specified folder and all of its contents. This is done
     * recursively and deletes the lowest level entries and then works its way
     * back up the hierarchy.
     *
     * @param dir The folder or file to be deleted.
     */
    private void deleteDir(File dir) {
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                if (f.isDirectory()) {
                    deleteDir(f);
                }
                f.delete();
            }
        }
        dir.delete();
    }
}
