package org.minetweak.plugins;

import com.google.common.eventbus.Subscribe;
import org.minetweak.Minetweak;
import org.minetweak.event.server.ServerInitializedEvent;
import org.minetweak.groovy.GroovyLoader;

/**
 * Simple hook to initialize plugins once the server is initialized
 */
public class PluginLoadingHook {
    @Subscribe
    public void onServerInitialized(ServerInitializedEvent event) {
        PluginManager.initialize();

        // Load Groovy Scripts
        if (!GroovyLoader.isLoaded()) {
            Minetweak.info("Unable to load Groovy Scripts!");
        }
    }
}
