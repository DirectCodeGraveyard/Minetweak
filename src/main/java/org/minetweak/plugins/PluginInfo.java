package org.minetweak.plugins;

public class PluginInfo {
    private String main;
    private String name;
    private String description;
    private boolean isBukkitPlugin;

    public PluginInfo(String mainClass, String pluginName, String description) {
        this.main = mainClass;
        this.name = pluginName;
        this.description = description;
        this.isBukkitPlugin = false;
    }

    public PluginInfo(String mainClass, String pluginName, String description, boolean isBukkitPlugin) {
        this.main = mainClass;
        this.name = pluginName;
        this.description = description;
        this.isBukkitPlugin = isBukkitPlugin;
    }

    /**
     * Get the name of the main class
     * @return name of main class
     */
    public String getMainClass() {
        return main;
    }

    /**
     * Gets the name of the plugin
     * @return name of plugin
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the plugin
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if Plugin is a Bukkit Plugin
     */
    public boolean isBukkitPlugin() {
        return isBukkitPlugin;
    }
}
