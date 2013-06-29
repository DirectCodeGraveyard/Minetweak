package org.minetweak.command;

import net.minecraft.src.EnumChatFormatting;
import org.minetweak.Minetweak;
import org.minetweak.Server;
import org.minetweak.entity.Player;

public class CommandOp extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(EnumChatFormatting.AQUA + "Usage: /op <player>");
            return;
        }
        Player targetPlayer = Minetweak.getPlayerByName(args[0]);

        Server.opPlayer(args[0]);

        if (Minetweak.isPlayerOnline(targetPlayer.getName())) {
            targetPlayer.sendMessage("You have been opped by: " + sender.getName());
            sender.sendMessage("You opped " + targetPlayer.getName());
        } else {
            sender.sendMessage("You opped " + targetPlayer.getName() + " which is offline at the moment");
        }
    }

}
