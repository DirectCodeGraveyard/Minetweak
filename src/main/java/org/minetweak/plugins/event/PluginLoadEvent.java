package org.minetweak.plugins.event;

import org.minetweak.Minetweak;
import org.minetweak.plugins.PluginInfo;

public class PluginLoadEvent extends PluginEvent {
    private PluginInfo pluginInfo;

    public PluginLoadEvent(PluginInfo pluginInfo) {
        this.pluginInfo = pluginInfo;
    }

    public PluginInfo getPluginInfo() {
        return pluginInfo;
    }

    public void registerListener(Object object) {
        Minetweak.registerListener(object);
    }
}
