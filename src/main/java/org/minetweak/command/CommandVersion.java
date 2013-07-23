package org.minetweak.command;

import net.minecraft.utils.enums.EnumChatFormatting;
import org.minetweak.Minetweak;

public class CommandVersion extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 0) {
            sender.sendMessage("Usage: /version");
            return;
        }

        sender.sendMessage("You are running " + EnumChatFormatting.BLUE + "Minetweak" + EnumChatFormatting.RESET + " v" + Minetweak.getAPIVersion() + ", using Minecraft v" + Minetweak.getMinecraftVersion());
    }

}
