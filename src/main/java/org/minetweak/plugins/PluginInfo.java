package org.minetweak.plugins;

public class PluginInfo {
    private String MainClass;

    public PluginInfo(String mainClass) {
        MainClass = mainClass;
    }

    public String getMainClass() {
        return MainClass;
    }
}
