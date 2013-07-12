package org.bukkit.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.minetweak.Minetweak;

import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class PluginLogger extends Logger {
    private String pluginName;

    public PluginLogger(JavaPlugin context) {
        super(context.getClass().getCanonicalName(), null);
        String prefix = context.getDescription().getPrefix();
        pluginName = prefix != null ? "[" + prefix + "] " : "[" + context.getDescription().getName() + "] ";
        setParent(Minetweak.getLogger().getLogger());
        setLevel(Level.ALL);
    }
}