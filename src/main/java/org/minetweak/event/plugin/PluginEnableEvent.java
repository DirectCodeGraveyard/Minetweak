package org.minetweak.event.plugin;

import org.minetweak.Minetweak;
import org.minetweak.command.CommandExecutor;
import org.minetweak.plugins.PluginInfo;

public class PluginEnableEvent extends PluginEvent {
    private PluginInfo pluginInfo;

    public PluginEnableEvent(PluginInfo pluginInfo) {
        this.pluginInfo = pluginInfo;
    }

    public PluginInfo getPluginInfo() {
        return pluginInfo;
    }

    public void registerListener(Object object) {
        Minetweak.registerListener(object);
    }

    public void registerCommand(String name, CommandExecutor executor) {
        Minetweak.registerCommand(name, executor);
    }
}
