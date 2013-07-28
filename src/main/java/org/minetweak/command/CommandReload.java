package org.minetweak.command;

import org.minetweak.plugins.PluginManager;
import org.minetweak.server.Server;

public class CommandReload extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (sender.hasPermission("minetweak.command.pardon")) {
            Server.sendToOps("Reloading Plugins");
            PluginManager.reloadPlugins();
            Server.sendToOps("Done Reloading Plugins");
            return;
        }
        noPermission(sender, "reload plugins");
    }

    @Override
    public String getHelpInfo() {
        return "Reloads Plugins";
    }
}
