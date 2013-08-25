package org.minetweak.plugins;

import org.minetweak.command.CommandExecutor;
import org.minetweak.language.LanguageObject;
import org.minetweak.util.TweakLogger;

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
     * Note: Do not unregister commands and listeners here.
     * Un-registration is done automatically.
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
     *
     * @param languageObject Language object to register
     */
    public void registerLanguageObject(LanguageObject languageObject);

    /**
     * Get the translated object name
     */
    public String getTranslatedName(LanguageObject languageObject);

    /**
     * Registers an Event Listener
     *
     * @param object the listener
     */
    public void registerListener(Object object);

    /**
     * Registers a Command for this Plugin
     *
     * @param label    the command label
     * @param executor the command executor
     */
    public void registerCommand(String label, CommandExecutor executor);

    /**
     * Sets the Plugin's Logger
     *
     * @param logger logger
     */
    public void setLogger(TweakLogger logger);

    /**
     * Gets the Plugins's Logger
     *
     * @return logger
     */
    public TweakLogger getLogger();
}
