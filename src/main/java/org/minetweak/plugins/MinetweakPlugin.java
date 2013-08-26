package org.minetweak.plugins;

import org.minetweak.Minetweak;
import org.minetweak.command.CommandExecutor;
import org.minetweak.language.LanguageObject;
import org.minetweak.util.TweakLogger;

import java.util.ArrayList;

/**
 * Base Class for Minetweak Plugins
 */
public abstract class MinetweakPlugin implements IPlugin {
    private PluginInfo pluginInfo;
    private ArrayList<String> commands = new ArrayList<String>();
    private ArrayList<Object> listeners = new ArrayList<Object>();
    private ArrayList<LanguageObject> languageObjects = new ArrayList<LanguageObject>();
    private TweakLogger logger;

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void purgeRegistrations() {
        for (String command : commands) {
            Minetweak.unregisterCommand(command);
        }
        for (Object listener : listeners) {
            Minetweak.getEventBus().unregister(listener);
        }
    }

    @Override
    public PluginInfo getPluginInfo() {
        return pluginInfo;
    }

    @Override
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

    @Override
    public void registerLanguageObject(LanguageObject languageObject) {
        languageObjects.add(languageObject);
    }

    @Override
    public String getTranslatedName(LanguageObject languageObject) {
        return languageObject.getTranslatedName();
    }

    @Override
    public void setLogger(TweakLogger logger) {
        this.logger = logger;
    }

    @Override
    public TweakLogger getLogger() {
        return logger;
    }

    @Override
    public void onLoad() {
        // This is not required
    }
}
