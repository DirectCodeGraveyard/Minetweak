package net.minecraft.utils.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatAllowedCharacters {
    /**
     * This String have the characters allowed in any text drawing of minecraft.
     */
    public static final String allowedCharacters = getAllowedCharacters();

    /**
     * Load the font.txt resource file, that is on UTF-8 format. This file contains the characters that minecraft can
     * render Strings on screen.
     */
    private static String getAllowedCharacters() {
        String var0 = "";

        try {
            BufferedReader var1 = new BufferedReader(new InputStreamReader(ChatAllowedCharacters.class.getResourceAsStream("/font.txt"), "UTF-8"));
            String var2;

            while ((var2 = var1.readLine()) != null) {
                if (!var2.startsWith("#")) {
                    var0 = var0 + var2;
                }
            }

            var1.close();
        } catch (Exception ignored) {
        }

        return var0;
    }

    public static boolean isAllowedCharacter(char par0) {
        return par0 != 167 && (allowedCharacters.indexOf(par0) >= 0 || par0 > 32) || par0 == ' ';
    }

    /**
     * Filter string by only keeping those characters for which isAllowedCharacter() returns true.
     */
    public static String filerAllowedCharacters(String par0Str) {
        StringBuilder var1 = new StringBuilder();
        char[] var2 = par0Str.toCharArray();

        for (char var5 : var2) {
            if (isAllowedCharacter(var5)) {
                var1.append(var5);
            }
        }

        return var1.toString();
    }
}
