package org.minetweak.plugins.event;

import org.minetweak.Minetweak;
import org.minetweak.plugins.PluginInfo;

public class PluginDisableEvent extends PluginEvent {
    private PluginInfo pluginInfo;

    public PluginDisableEvent(PluginInfo pluginInfo) {
        this.pluginInfo = pluginInfo;
    }

    public PluginInfo getPluginInfo() {
        return pluginInfo;
    }

    public void unregisterListener(Object object) {
        Minetweak.getEventBus().unregister(object);
    }
}