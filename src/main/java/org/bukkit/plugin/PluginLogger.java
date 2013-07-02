package org.bukkit.plugin;

import net.minecraft.server.MinecraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PluginLogger extends Logger {
    private String pluginName;

    public PluginLogger(JavaPlugin context) {
        super(context.getClass().getCanonicalName(), null);
        String prefix = context.getDescription().getPrefix();
        pluginName = prefix != null ? "[" + prefix + "] " : "[" + context.getDescription().getName() + "] ";
        setParent(MinecraftServer.getServer().getLogAgent().func_120013_a());
        setLevel(Level.ALL);
    }
}
