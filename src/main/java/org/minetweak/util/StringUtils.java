package org.minetweak.util;

public class StringUtils {
    public static String[] dropFirstString(String[] par0ArrayOfStr) {
        String[] var1 = new String[par0ArrayOfStr.length - 1];
        System.arraycopy(par0ArrayOfStr, 1, var1, 0, par0ArrayOfStr.length - 1);
        return var1;
    }

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

    public static boolean isInteger(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(c >= '0' && c <= '9'))
                return false;
        }
        return true;
    }
}
