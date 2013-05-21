package main.programStructure;

/**
 * @author David Gronlund
 */
public class Util {

    public static String classLocationToQualifier(String location) {
        return location.replace(".class", "").replace("/", ".");
    }

    private String getFile(String location) {
        String[] parts = location.split("\\\\"); //First backslash pair clears
        //regex for special characters,
        //second pair is actual pair.
        return parts[parts.length - 1];
    }

    private String getParentDirectories(String location) {
        String[] parts = location.split("\\\\"); //First backslash pair clears
        //regex for special characters,
        //second pair is actual pair.
        String dir = "";
        for (int i = 0; i < parts.length - 1; i++) {
            dir += parts[i] + "\\";
        }
        return dir;
    }
}
