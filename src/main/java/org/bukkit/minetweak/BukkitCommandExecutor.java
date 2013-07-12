package org.bukkit.minetweak;

import org.minetweak.command.CommandExecutor;
import org.minetweak.command.CommandSender;

public class BukkitCommandExecutor extends CommandExecutor {
    private org.bukkit.command.CommandExecutor executor;

    public BukkitCommandExecutor(org.bukkit.command.CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        executor.onCommand(sender, MinetweakHelper.commands.get(overallCommand), overallCommand, args);
    }
}
