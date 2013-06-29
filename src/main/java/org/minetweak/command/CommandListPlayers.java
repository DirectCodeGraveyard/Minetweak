package org.minetweak.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EnumChatFormatting;

public class CommandListPlayers extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 0) {
            sender.sendMessage("Usage: /players");
            return;
        }

        sender.sendMessage(EnumChatFormatting.AQUA + "Online Players: " + MinecraftServer.getServer().getConfigurationManager().getPlayerListAsString());
    }

}
