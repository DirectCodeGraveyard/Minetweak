package org.bukkit.command;

import org.minetweak.command.CommandSender;

public interface CommandExecutor {
    public boolean onCommand(CommandSender sender, PluginCommand command, String label, String[] split);
}
