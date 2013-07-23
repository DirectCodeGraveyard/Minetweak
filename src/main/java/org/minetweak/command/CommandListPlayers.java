package org.minetweak.command;

import net.minecraft.server.MinecraftServer;

public class CommandListPlayers extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 0) {
            sender.sendMessage("Usage: /players");
            return;
        }

        sender.sendMessage("Online Players: " + MinecraftServer.getServer().getConfigurationManager().getPlayerListAsString());
    }

    @Override
    public String getHelpInfo() {
        return "Lists Online Players";
    }

}
