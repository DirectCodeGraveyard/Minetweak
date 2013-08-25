package org.minetweak.util;

public class ReflectionUtils {
    /**
     * Checks if a Class Exists
     *
     * @param name Class name
     * @return if class exists
     */
    public static boolean classExists(String name) {
        try {
            Class.forName(name);
        } catch (ClassNotFoundException ignored) {
            return false;
        }
        return true;
    }
}
