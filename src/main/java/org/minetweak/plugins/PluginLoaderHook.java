package org.minetweak.plugins;

import com.google.common.eventbus.Subscribe;
import org.minetweak.event.server.ServerInitializedEvent;

public class PluginLoaderHook {
    @Subscribe
    public void onServerInitialized(ServerInitializedEvent event) {
        PluginLoader.initialize();
    }
}
