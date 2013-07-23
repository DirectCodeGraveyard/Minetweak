package org.minetweak.command;

import org.minetweak.Server;
import org.minetweak.plugins.PluginManager;

public class CommandReload extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (sender.hasPermission("minetweak.command.pardon")) {
            Server.broadcastMessage("Reloading Plugins");
            PluginManager.reloadPlugins();
            Server.broadcastMessage("Done Reloading Plugins");
            return;
        }
        noPermission(sender, "reload plugins");
    }
}
