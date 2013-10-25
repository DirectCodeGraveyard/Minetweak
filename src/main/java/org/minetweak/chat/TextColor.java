package org.minetweak.chat;

import org.minetweak.config.GameConfig;
import org.minetweak.config.Property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "UnusedDeclaration"})
public enum TextColor {
    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_AQUA('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f'),
    OBFUSCATED('k', true),
    BOLD('l', true),
    STRIKETHROUGH('m', true),
    UNDERLINE('n', true),
    ITALIC('o', true),
    RESET('r');
    private static final Map<Character, TextColor> ids = new HashMap<Character, TextColor>();
    private static final Map<CharSequence, TextColor> colors = new HashMap<CharSequence, TextColor>();
    private static final Pattern pattern = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");
    private final char id;
    private final boolean isFormatting;
    private final String out;

    private static ArrayList<String> colorNodes = new ArrayList<String>();

    private TextColor(char par3) {
        this(par3, false);
    }

    private TextColor(char par3, boolean par4) {
        this.id = par3;
        this.isFormatting = par4;
        this.out = "\u00a7" + par3;
    }

    public static void initializeColorNodes() {
        colorNodes.add(String.valueOf('\u00a7') + "0");
        colorNodes.add(String.valueOf('\u00a7') + "1");
        colorNodes.add(String.valueOf('\u00a7') + "2");
        colorNodes.add(String.valueOf('\u00a7') + "3");
        colorNodes.add(String.valueOf('\u00a7') + "4");
        colorNodes.add(String.valueOf('\u00a7') + "5");
        colorNodes.add(String.valueOf('\u00a7') + "6");
        colorNodes.add(String.valueOf('\u00a7') + "7");
        colorNodes.add(String.valueOf('\u00a7') + "8");
        colorNodes.add(String.valueOf('\u00a7') + "9");
        colorNodes.add(String.valueOf('\u00a7') + "a");
        colorNodes.add(String.valueOf('\u00a7') + "b");
        colorNodes.add(String.valueOf('\u00a7') + "c");
        colorNodes.add(String.valueOf('\u00a7') + "d");
        colorNodes.add(String.valueOf('\u00a7') + "e");
        colorNodes.add(String.valueOf('\u00a7') + "f");
        colorNodes.add(String.valueOf('\u00a7') + "k");
        colorNodes.add(String.valueOf('\u00a7') + "l");
        colorNodes.add(String.valueOf('\u00a7') + "m");
        colorNodes.add(String.valueOf('\u00a7') + "n");
        colorNodes.add(String.valueOf('\u00a7') + "o");
        colorNodes.add(String.valueOf('\u00a7') + "r");
    }

    public static ArrayList<String> getColorNodes() {
        return colorNodes;
    }

    public char getID() {
        return this.id;
    }

    public boolean isFormatting() {
        return this.isFormatting;
    }

    public boolean isColor() {
        return !this.isFormatting && this != RESET;
    }

    public String getName() {
        return this.name().toLowerCase();
    }

    public String toString() {
        return this.out;
    }

    public static TextColor getByName(String requested) {
        if (requested==null) {
            return null;
        }
        requested = requested.toLowerCase();
        if (!colors.containsKey(requested)) {
            return null;
        }
        return colors.get(requested);
    }

    public static Collection<CharSequence> getList(boolean par0, boolean par1) {
        ArrayList<CharSequence> var2 = new ArrayList<CharSequence>();
        TextColor[] var3 = values();
        int var4 = var3.length;

        for (TextColor var6 : var3) {
            if ((!var6.isColor() || par0) && (!var6.isFormatting() || par1)) {
                var2.add(var6.getName());
            }
        }

        return var2;
    }

    public static TextColor getConfigurableColor(String name, TextColor defaultColor) {
        String colorName = GameConfig.get(new Property(name).addComment("Type: Color"));
        if (colorName==null) {
            return defaultColor;
        }
        TextColor color = getByName(colorName);
        if (color==null) {
            return defaultColor;
        }
        return color;
    }

    static {
        TextColor[] var0 = values();
        int var1 = var0.length;

        for (TextColor var3 : var0) {
            ids.put(var3.getID(), var3);
            colors.put(var3.getName(), var3);
        }
    }
}