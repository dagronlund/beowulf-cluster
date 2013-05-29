package runtime;

/**
 * @author David Gronlund
 */
public class Util {

    /**
     * Returns the qualifier of the specified relative class location. (e.g. for
     * /main/something/Program.class this would return main.something.Program)
     *
     * @param location The relative location of the class
     * @return The qualifier of the class
     */
    public static String classLocationToQualifier(String location) {
        if (location.startsWith("/") || location.startsWith("\\")) {
            location = location.substring(1);
        }
        return location.replace(".class", "").replace("/", ".").replace("\\", ".");
    }

    /**
     * Returns the relative location of the specified class qualifier. (e.g. for
     * main.something.Program this would return main/something/Program.class)
     *
     * @param qualifier The qualifier of the class
     * @return The relative location of the class
     */
    public static String qualifierToClassLocation(String qualifier) {
        return "/" + qualifier.replace(".", "\\") + ".class";
    }
}
