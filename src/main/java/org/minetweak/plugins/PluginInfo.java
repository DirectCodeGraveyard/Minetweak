package org.minetweak.plugins;

public class PluginInfo {
    /**
     * The Plugin's Main class
     */
    private String main;

    /**
     * The Plugin's Name
     */
    private String name;

    /**
     * A Description of a Plugin
     */
    private String description;

    /**
     * Specifies if this is a Bukkit Plugin
     */
    private boolean isBukkitPlugin;

    /**
     * The Authors Name
     */
    private String author;

    public PluginInfo(String mainClass, String pluginName, String description) {
        this.main = mainClass;
        this.name = pluginName;
        this.description = description;
        this.isBukkitPlugin = false;
    }

    public PluginInfo(String mainClass, String pluginName, String description, String author) {
        this.main = mainClass;
        this.name = pluginName;
        this.description = description;
        this.isBukkitPlugin = false;
        this.author = author;
    }

    public PluginInfo(String mainClass, String pluginName, String description, boolean isBukkitPlugin) {
        this.main = mainClass;
        this.name = pluginName;
        this.description = description;
        this.isBukkitPlugin = isBukkitPlugin;
    }

    /**
     * Get the name of the main class
     *
     * @return name of main class
     */
    public String getMainClass() {
        return main;
    }

    /**
     * Gets the name of the plugin
     *
     * @return name of plugin
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the plugin
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if Plugin is a Bukkit Plugin
     *
     * @return if it is a bukkit plugin
     */
    public boolean isBukkitPlugin() {
        return isBukkitPlugin;
    }

    /**
     * Checks if a Plugin is a Minetweak Plugin
     *
     * @return if it is a minetweak plugin
     */
    public boolean isMinetweakPlugin() {
        return !isBukkitPlugin;
    }

    /**
     * Gets the Author of the Plugin
     *
     * @return plugin's author
     */
    public String getAuthor() {
        return author;
    }
}
