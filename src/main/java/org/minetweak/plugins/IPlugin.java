package org.minetweak.plugins;

/**
 * Base Class for Plugins
 */
public interface IPlugin {
    /**
     * Called when plugins are loaded before server is started.
     */
    public void onEnable();

    /**
     * Called when plugins are unloaded before server is stopped.
     */
    public void onDisable();

    /**
     * Gets Plugin Info
     */
    public PluginInfo getPluginInfo();

    /** Allows you to override Plugin Info on the fly
     * @param pluginInfo the plugin info instance to set
     */
    public void setPluginInfo(PluginInfo pluginInfo);
}