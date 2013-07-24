package org.minetweak.command;

import net.minecraft.server.MinecraftServer;
import org.minetweak.permissions.PermissionsLoader;
import org.minetweak.permissions.ServerOps;

public class CommandLoadData extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 0) {
            sender.sendMessage("Usage: /" + overallCommand);
            return;
        }
        if (!sender.hasPermission("minetweak.command.loaddata")) {
            noPermission(sender, "load server data");
            return;
        }
        sender.sendMessage("Loading Permissions");
        PermissionsLoader.load();
        sender.sendMessage("Loading Whitelist");
        ServerOps.load();
        sender.sendMessage("Loading Bans");
        MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().loadBanList();
    }

    @Override
    public String getHelpInfo() {
        return "Loads Server Data";
    }
}
