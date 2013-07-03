package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.Server;
import org.minetweak.entity.Player;

public class CommandOp extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /op <player>");
            return;
        }
        Player targetPlayer = Minetweak.getPlayerByName(args[0]);

        Server.opPlayer(args[0]);

        if (Minetweak.isPlayerOnline(args[0])) {
            targetPlayer.sendMessage("You have been opped by: " + sender.getName());
            sender.sendMessage("You opped " + args[0]);
        } else {
            sender.sendMessage("You opped " + args[0] + " which is offline");
        }
    }

}
