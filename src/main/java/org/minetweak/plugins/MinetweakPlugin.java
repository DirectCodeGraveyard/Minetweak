package org.minetweak.plugins;

import org.minetweak.Minetweak;
import org.minetweak.command.CommandExecutor;
import org.minetweak.language.LanguageObject;

import java.util.ArrayList;

/**
 * Base Class for Plugins
 */
public abstract class MinetweakPlugin implements IPlugin {
    private PluginInfo pluginInfo;
    private ArrayList<String> commands = new ArrayList<String>();
    private ArrayList<Object> listeners = new ArrayList<Object>();
    private ArrayList<LanguageObject> languageObjects = new ArrayList<LanguageObject>();

    /**
     * Called when plugins are loaded before server is started.
     */
    public void onEnable() {
    }

    /**
     * Called when plugins are unloaded before server is stopped.
     * Note: Do not unregister commands and listeners here.
     * Un-registration is done automatically.
     */
    public void onDisable() {
    }

    /**
     * Un-registers all Commands and Listeners for this plugin
     */
    public void purgeRegistrations() {
        for (String command : commands) {
            Minetweak.unregisterCommand(command);
        }
        for (Object listener : listeners) {
            Minetweak.getEventBus().unregister(listener);
        }
    }

    /**
     * Gets Plugin Info
     */
    public PluginInfo getPluginInfo() {
        return pluginInfo;
    }

    /**
     * Allows you to override Plugin Info on the fly
     *
     * @param pluginInfo the plugin info instance to set
     */
    public void setPluginInfo(PluginInfo pluginInfo) {
        this.pluginInfo = pluginInfo;
    }

    @Override
    public void registerCommand(String label, CommandExecutor executor) {
        commands.add(label);
        Minetweak.registerCommand(label, executor);
    }

    @Override
    public void registerListener(Object listener) {
        listeners.add(listener);
        Minetweak.registerListener(listener);
    }

    /**
     * Register a language object
     *
     * @param languageObject Language object to register
     */
    public void registerLanguageObject(LanguageObject languageObject) {
        languageObjects.add(languageObject);
    }

    /**
     * Get the translated object name
     */
    public String getTranslatedName(LanguageObject languageObject) {
        return languageObject.getTranslatedName();
    }
}
