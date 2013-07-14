package org.minetweak.command;

import net.minecraft.src.EnumChatFormatting;
import org.minetweak.Minetweak;
import org.minetweak.Server;
import org.minetweak.command.CommandExecutor;
import org.minetweak.command.CommandSender;
import org.minetweak.entity.Player;
import org.minetweak.plugins.PluginLoader;

public class CommandReload extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (sender.hasPermission("minetweak.command.pardon")) {
            Server.broadcastMessage("Reloading Plugins");
            PluginLoader.reloadPlugins();
            Server.broadcastMessage("Done Reloading Plugins");
            return;
        }
        sender.sendMessage(EnumChatFormatting.RED + "You do not have permission to reload plugins.");
    }
}
