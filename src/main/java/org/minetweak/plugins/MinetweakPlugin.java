package org.minetweak.plugins;

/**
 * Base Class for Plugins
 */
public abstract class MinetweakPlugin {
    /**
     * Called when plugins are loaded before server is started.
     */
    public void onEnable() {}

    /**
     * Called when plugins are unloaded before server is stopped.
     */
    public void onDisable() {}
}
