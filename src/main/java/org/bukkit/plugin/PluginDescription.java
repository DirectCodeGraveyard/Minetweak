package org.bukkit.plugin;

public class PluginDescription {
    private String prefix;
    private String name;

    public PluginDescription(String prefix, String name) {
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
