package org.minetweak.config;

public class MinetweakConfig {
    private static Configuration config = new Configuration("minetweak.cfg");

    public static void initialize() {
        header("This is the Minetweak main configuration file");
        newLine();
        get(new Property("player.autoOp").addComment("Allows for Whitelisted players to be oped on join"));
        get(new Property("server.stop.message").addComment("Sets the disconnect message to be displayed to users when the server is stopping"));
        get(new Property("server.generator-settings"));
        get(new Property("server.op-permission-level"));
        get(new Property("server.allow-nether"));
        get(new Property("server.level-name"));
        get(new Property("server.enable-query"));
        get(new Property("server.allow-flight"));
        get(new Property("server.port"));
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

    public static Integer getInteger(String key) {
        return Integer.parseInt(get(key));
    }
}
