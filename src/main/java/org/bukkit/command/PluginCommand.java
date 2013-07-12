package org.bukkit.command;

import org.bukkit.minetweak.MinetweakHelper;

public class PluginCommand {
    private String name;
    private CommandExecutor executor;

    public PluginCommand(String name) {
        this.name = name;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
        MinetweakHelper.addCommand(this);
    }

    public String getName() {
        return name;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }
}
