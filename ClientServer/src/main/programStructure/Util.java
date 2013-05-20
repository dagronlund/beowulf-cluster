package main.programStructure;

/**
 * @author David Gronlund
 */
public class Util {
    
    public static String classLocationToQualifier(String location) {
        return location.replace(".class", "").replace("/", ".");
    }
}
