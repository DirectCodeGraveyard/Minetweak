package org.bukkit.plugin;

public class PluginDescriptionFile {
    private String prefix;
    private String name;

    public PluginDescriptionFile(String prefix, String name) {
        this.prefix = prefix;
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getName() {
        return name;
    }
}
