package org.minetweak.config;

import org.minetweak.Minetweak;

import java.io.File;
import java.util.logging.Level;

public class MinetweakConfig {
    private static File configFile = new File("minetweak.cfg");
    private static Configuration config = new Configuration(configFile);

    public static void initialize() {
        header("This is the Minetweak main configuration file");
        get(new Property("player.autoOp").addComment("Allows for Whitelisted players to be oped on join"));
        get(new Property("server.stop.message").addComment("Sets the disconnect message to be displayed to users when the server is stopping"));
        String logLevel = get(new Property("minetweak.log.level", "INFO").addComment("Log Level for Minetweak: Values: INFO/DEBUG/FINE/SEVERE/WARNING"));
        Minetweak.getLogger().getLogger().setLevel(Level.parse(logLevel));
    }

    public static void set(String key, String value) {
        config.set(key, value);
    }

    public static String get(String key) {
        return config.get(key);
    }

    public static String get(Property property) {
        return config.get(property);
    }

    public static void newLine() {
        config.newLine();
    }

    public static void lock() {
        config.lock();
    }

    public static void comment(String comment) {
        config.comment(comment);
    }

    public static void header(String header) {
        config.header(header);
    }

    public static void unlock() {
        config.unlock();
    }

    public static int getInteger(String key) {
        return getInteger(key, 0);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, true);
    }

    public static boolean getBoolean(String key, boolean value) {
        return get(new Property(key, "" + value)).equals("true");
    }

    public static String get(String key, String value) {
        return get(new Property(key, value));
    }

    public static int getInteger(String key, Integer value) {
        return Integer.parseInt(get(new Property(key, "" + value.toString())));
    }

    public static File getConfigFile() {
        return configFile;
    }
}
