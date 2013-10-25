package org.minetweak.config;

import org.minetweak.Minetweak;

import java.io.File;
import java.util.logging.Level;

/**
 * Represents the Minetweak Config File
 */
public class TweakConfig {
    private static File configFile = new File("minetweak.cfg");
    private static Configuration config = new Configuration(configFile);

    /**
     * Initializes Minetweak Config
     */
    public static void initialize() {
        header("Minetweak Configuration File");
        newLine();
        get(new Property("server.stop.message").addComment("Sets the disconnect message to be displayed to users when the server is stopping"));
        String logLevel = get(new Property("minetweak.log.level", "INFO").addComment("Log Level for Minetweak: Values: INFO/DEBUG/FINE/SEVERE/WARNING"));
        Minetweak.getLogger().getLogger().setLevel(Level.parse(logLevel));
    }

    /**
     * Sets a Config Option
     *
     * @param key   Config Key
     * @param value Config Value
     */
    public static void set(String key, String value) {
        config.set(key, value);
    }

    /**
     * Gets a Config Option
     *
     * @param key Config Key
     * @return Config Value
     */
    public static String get(String key) {
        return config.get(key);
    }

    /**
     * Gets a Config Property
     *
     * @param property Config Property
     * @return Config Value
     */
    public static String get(Property property) {
        return config.get(property);
    }

    /**
     * Creates a New Line in the Config File
     */
    public static void newLine() {
        config.newLine();
    }

    /**
     * Locks the Config File
     */
    public static void lock() {
        config.lock();
    }

    /**
     * Adds a Comment to the Config File
     *
     * @param comment Comment
     */
    public static void comment(String comment) {
        config.comment(comment);
    }

    /**
     * Sets the Config File Header
     *
     * @param header Header
     */
    public static void header(String header) {
        config.header(header);
    }

    /**
     * Unlocks the Config File
     */
    public static void unlock() {
        config.unlock();
    }

    /**
     * Gets an Option as an Integer
     *
     * @param key Config Key
     * @return Config Value as Integer
     */
    public static int getInteger(String key) {
        return getInteger(key, 0);
    }

    /**
     * Gets an Option as a boolean
     *
     * @param key Config Key
     * @return Config Value
     */
    public static boolean getBoolean(String key) {
        return getBoolean(key, true);
    }

    /**
     * Gets an Option as a boolean with a default value
     *
     * @param key   Config Key
     * @param value Default Value
     * @return Config Value
     */
    public static boolean getBoolean(String key, boolean value) {
        return get(new Property(key, String.valueOf(value))).equals("true");
    }

    /**
     * Gets a Config Option
     *
     * @param key   Config Key
     * @param value Default Value
     * @return Config Value
     */
    public static String get(String key, String value) {
        return get(new Property(key, value));
    }

    /**
     * Gets an Option as an Integer
     *
     * @param key   Config Key
     * @param value Default Value
     * @return Config Value as Integer
     */
    public static int getInteger(String key, Integer value) {
        return Integer.parseInt(get(new Property(key, value.toString())));
    }

    /**
     * Gets the Config File
     *
     * @return Config File
     */
    public static File getConfigFile() {
        return configFile;
    }
}
