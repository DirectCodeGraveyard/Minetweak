package org.minetweak.plugins;

public class PluginInfo {
    private String MainClass;
    private String Name;

    public PluginInfo(String mainClass, String pluginName) {
        this.MainClass = mainClass;
        this.Name = pluginName;
    }

    /**
     * Get the name of the main class
     * @return name of main class
     */
    public String getMainClass() {
        return MainClass;
    }

    /**
     * Gets the name of the plugin
     * @return name of plugin
     */
    public String getName() {
        return Name;
    }
}
