package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.ChatFormatting;
import org.minetweak.entity.Player;

public class CommandTp extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length>2 || args.length==0) {
            sender.sendMessage(getUsage());
            return;
        }
        sender.sendMessage("Teleporting....");
        Player player;
        Player target;
        if (args.length==1) {
            player = Minetweak.getPlayerByName(sender.getName());
            target = Minetweak.getPlayerByName(args[0]);
        } else {
            player = Minetweak.getPlayerByName(args[0]);
            target = Minetweak.getPlayerByName(args[1]);
        }
        if (player==null) {
            sender.sendMessage(ChatFormatting.RED + args[0] + " is not online.");
            return;
        }
        if (target==null) {
            sender.sendMessage(ChatFormatting.RED + args[1] + " is not online.");
            return;
        }
        sender.sendMessage(ChatFormatting.GOLD + "Teleporting " + ChatFormatting.BLUE + player.getName() + ChatFormatting.RESET + " to " + ChatFormatting.BLUE + target.getName());
        player.teleportToPosition(target.getX(), target.getY(), target.getZ());
    }

    public String getUsage() {
        return "Usage: /tp [player] [target-player]";
    }
}
