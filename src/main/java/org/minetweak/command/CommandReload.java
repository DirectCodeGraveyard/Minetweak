package org.minetweak.command;

import org.minetweak.command.CommandExecutor;
import org.minetweak.command.CommandSender;
import org.minetweak.plugins.PluginLoader;

public class CommandReload extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        sender.sendMessage("Reloading Plugins");
        PluginLoader.reloadPlugins();
        sender.sendMessage("Done Reloading Plugins");
    }
}
