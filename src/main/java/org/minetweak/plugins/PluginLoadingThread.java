package org.minetweak.plugins;

/**
 * Loads each plugin in a thread
 */
public class PluginLoadingThread extends Thread {
    private IPlugin plugin;

    /**
     * Creates an instance of PluginLoadingThread
     * @param plugin plugin to load
     */
    protected PluginLoadingThread(IPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Loads the plugin and adds it to the list of enabled plugins
     */
    @Override
    public void run() {
        plugin.onEnable();
        PluginLoader.enabledPlugins.add(plugin.getPluginInfo().getName());
    }
}
