package org.minetweak.plugins;

import com.google.common.eventbus.Subscribe;
import org.minetweak.event.server.ServerInitializedEvent;

/**
 * Simple hook to initialize plugins once the server is initialized
 */
public class PluginLoadingHook {
    @Subscribe
    public void onServerInitialized(ServerInitializedEvent event) {
        PluginManager.initialize();

        // Load Groovy Scripts (Bugged)
        // if (!GroovyLoader.isLoaded()) {
        //    Minetweak.info("Unable to load Groovy Scripts!");
        // }
    }
}
