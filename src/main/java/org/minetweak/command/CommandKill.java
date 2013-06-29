package org.minetweak.command;

import net.minecraft.src.EnumChatFormatting;
import org.minetweak.Minetweak;
import org.minetweak.entity.Player;

public class CommandKill extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(EnumChatFormatting.AQUA + "Usage: /kill <player>");
            return;
        }
        Player targetPlayer = Minetweak.getPlayerByName(args[0]);

        if (Minetweak.isPlayerOnline(args[0])) {
            sender.sendMessage(EnumChatFormatting.AQUA + "You have killed " + args[0]);
            targetPlayer.sendMessage(EnumChatFormatting.RED + "Ouch, that must have hurt");
            targetPlayer.killPlayer();
        } else {
            sender.sendMessage(EnumChatFormatting.RED + "You cannot kill a offline player");
        }
    }

}
