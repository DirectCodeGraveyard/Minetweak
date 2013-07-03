package org.minetweak.plugins;

public class PluginLoadingThread extends Thread {
    private MinetweakPlugin plugin;

    protected PluginLoadingThread(MinetweakPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.onEnable();
        PluginLoader.enabledPlugins.add(plugin.getName());
    }
}
