package org.minetweak.command;

import net.minecraft.src.EnumChatFormatting;
import org.minetweak.Minetweak;
import org.minetweak.entity.Player;

public class CommandDeop extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(EnumChatFormatting.AQUA + "Usage: /op <player>");
            return;
        }
        Player targetPlayer = Minetweak.getPlayerByName(args[0]);

        targetPlayer.deopPlayer();

        if (targetPlayer.isOnline()) {
            targetPlayer.sendMessage("You have been deopped by: " + sender.getName());
            sender.sendMessage("You deopped " + targetPlayer.getName());
        } else {
            sender.sendMessage("You deopped " + targetPlayer.getName() + " which is offline at the moment");
        }
    }

}
