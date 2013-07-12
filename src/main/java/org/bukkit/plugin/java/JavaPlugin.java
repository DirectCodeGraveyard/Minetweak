package org.bukkit.plugin.java;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLogger;
import org.minetweak.Server;
import org.minetweak.plugins.PluginInfo;
import org.minetweak.plugins.PluginLoader;

import java.io.File;

public abstract class JavaPlugin extends Plugin {
    private boolean isEnabled = false;
    private boolean initialized = false;
    private PluginLoader loader = null;
    private Server server = null;
    private File file = null;
    private File dataFolder = new File(".");
    private ClassLoader classLoader = PluginLoader.loader;
    private boolean naggable = true;
    private File configFile = null;
    private PluginLogger logger;
    private PluginDescriptionFile description;
    private PluginInfo pluginInfo;

    public PluginDescriptionFile getDescription() {
        return description;
    }

    public Server getServer() {
        return server;
    }

    public PluginInfo getPluginInfo() {
        return pluginInfo;
    }

    public void setPluginInfo(PluginInfo pluginInfo) {
        this.pluginInfo = pluginInfo;
        this.description = new PluginDescriptionFile(pluginInfo.getName(), pluginInfo.getName());
        this.logger = new PluginLogger(this);
    }

    public PluginCommand getCommand(String name) {
        return new PluginCommand(name);
    }
}
