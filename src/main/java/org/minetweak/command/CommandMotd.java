package org.minetweak.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EnumChatFormatting;
import org.minetweak.Minetweak;
import org.minetweak.entity.Player;
import org.minetweak.util.StringUtils;

public class CommandMotd extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        String motd = StringUtils.toString(args);
        Player player = Minetweak.getPlayerByName(sender.getName());
        if (motd.equals("")) {
            sender.sendMessage(MinecraftServer.getServer().getMOTD());
            return;
        }
        if (player==null || player.isOperator()) {
            MinecraftServer.getServer().setMOTD(motd);
            sender.sendMessage("Set MOTD!");
        } else {
            sender.sendMessage(EnumChatFormatting.RED + "Sorry! You do not have permission to set the MOTD.");
        }
    }
}