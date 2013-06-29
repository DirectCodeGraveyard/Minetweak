package org.minetweak.command;

import net.minecraft.src.EnumChatFormatting;

public class CommandSay extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(EnumChatFormatting.AQUA + "Usage: /say <message>");
        }
    }
}
