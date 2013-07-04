package org.minetweak.plugins;

public class PluginInfo {
    private String MainClass;

    public PluginInfo(String mainClass) {
        MainClass = mainClass;
    }

    /**
     * Get the name of the main class
     * @return name of main class
     */
    public String getMainClass() {
        return MainClass;
    }
}
