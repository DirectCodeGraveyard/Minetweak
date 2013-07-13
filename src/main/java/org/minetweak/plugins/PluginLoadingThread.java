package org.minetweak.plugins;

/**
 * Loads all plugins in a separate thread
 */
public class PluginLoadingThread extends Thread {

    /**
     * Loads the plugins and adds each one to the list of enabled plugins
     */
    @Override
    public void run() {
        for (IPlugin plugin : PluginLoader.plugins.values()) {
            plugin.onEnable();
            PluginLoader.enabledPlugins.add(plugin.getPluginInfo().getName());
        }
    }
}
