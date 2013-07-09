package org.minetweak.util;

public class StringUtils {
    /**
     * Drops the first String
     * @param array Array of Strings
     * @return Array of Strings without first String
     */
    public static String[] dropFirstString(String[] array) {
        String[] var1 = new String[array.length - 1];
        System.arraycopy(array, 1, var1, 0, array.length - 1);
        return var1;
    }

    /**
     * Makes a String out of a String array
     * @param array Array of Strings
     * @return stringed array
     */
    public static String toString(String[] array) {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (String string : array) {
            count++;
            if (count==array.length) {
                builder.append(string);
            } else {
                builder.append(string).append(" ");
            }
        }
        return builder.toString();
    }

    /**
     * Checks if a String is an Integer
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
