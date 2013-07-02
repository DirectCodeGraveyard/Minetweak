package org.bukkit.plugin.java;

import org.bukkit.plugin.PluginDescription;
import org.bukkit.plugin.PluginLogger;
import org.minetweak.Server;
import org.minetweak.plugins.MinetweakPlugin;
import org.minetweak.plugins.PluginLoader;

import java.io.File;

public abstract class JavaPlugin extends MinetweakPlugin {
    private boolean isEnabled = false;
    private boolean initialized = false;
    private PluginLoader loader = null;
    private Server server = null;
    private File file = null;
    private File dataFolder = null;
    private ClassLoader classLoader = null;
    private boolean naggable = true;
    private File configFile = null;
    private PluginLogger logger = null;
    private PluginDescription description;

    public PluginDescription getDescription() {
        return description;
    }

    public Server getServer() {
        return server;
    }
}
