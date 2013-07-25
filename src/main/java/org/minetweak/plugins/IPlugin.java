package org.minetweak.plugins;

import org.minetweak.language.LanguageObject;

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

    /**
     * Allows you to override Plugin Info on the fly
     *
     * @param pluginInfo the plugin info instance to set
     */
    public void setPluginInfo(PluginInfo pluginInfo);

    /**
     * This is called by PluginManager on disable to remove
     * all the commands and listeners created by the plugin
     */
    public void purgeRegistrations();

    /**
     * Register a language object
     */
    public void registerLanguageObject(LanguageObject languageObject);

    public String getTranslatedName(LanguageObject languageObject);
}
