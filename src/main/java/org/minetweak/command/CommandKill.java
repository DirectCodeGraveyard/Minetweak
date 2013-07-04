package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.entity.Player;

public class CommandKill extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /kill <player>");
            return;
        } else if (!sender.hasPermission("minetweak.command.kill")) {
            sender.sendMessage("You don't have the permissions to do that.");
            return;
        }
        Player targetPlayer = Minetweak.getPlayerByName(args[0]);

        if (Minetweak.isPlayerOnline(args[0])) {
            sender.sendMessage("You have killed " + args[0]);
            targetPlayer.sendMessage("Ouch, that must have hurt");
            targetPlayer.killPlayer();
        } else {
            sender.sendMessage("You cannot kill a offline player");
        }
    }

}
