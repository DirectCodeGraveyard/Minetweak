package org.minetweak.util;

import org.codehaus.groovy.runtime.DefaultGroovyMethods;

import java.util.List;

/**
 * String Utilities
 */
public class StringUtils {
    /**
     * Drops the first String in the given Array
     *
     * @param array Array of Strings
     * @return Array of Strings without first String
     */
    public static String[] dropFirstString(String[] array) {
        String[] var1 = new String[array.length - 1];
        System.arraycopy(array, 1, var1, 0, array.length - 1);
        return var1;
    }

    /**
     * Drops the first String in the given List
     *
     * @param list List of Strings
     * @return List of Strings without first String
     */
    public static List<String> dropFirstString(List<String> list) {
        list.remove(0);
        return list;
    }

    /**
     * Makes a String out of a String array
     *
     * @param array Array of Strings
     * @return stringed array
     */
    public static String toString(String[] array) {
        return DefaultGroovyMethods.join(array, " ");
    }

    /**
     * Makes a String out of a String List
     *
     * @param list List of Strings
     * @return Stringed List
     */
    public static String toString(List<String> list) {
        return DefaultGroovyMethods.join(list, " ");
    }

    /**
     * Checks if a String is an Integer
     *
     * @param s string to check
     * @return if the string is an integer
     */
    public static boolean isInteger(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(c >= '0' && c <= '9'))
                return false;
        }
        return true;
    }
}
