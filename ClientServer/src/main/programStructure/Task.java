package main.programStructure;

/**
 * @author David Gronlund
 */
public abstract class Task {

    private byte[] classData;
    private String className;

    /**
     *
     * @param classData This is the raw binary data that comprises the class to
     * run.
     * @param className This is the fully qualified name of the class to run
     * (i.e. main/code/Main.class) where the packages are separated by / and the
     * string is terminated by .class. This should be returned by getMainProgram
     * or getTask in UserProgramLoader.
     */
    public Task(byte[] classData, String className) {
        this.classData = classData;
        this.className = className.replace(".class", "").replace("/", ".");
    }
}
