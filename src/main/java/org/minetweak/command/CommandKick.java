package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.entity.Player;
import org.minetweak.util.StringUtils;

public class CommandKick extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /kick <player> [reason]");
            return;
        } else if (!sender.hasPermission("minetweak.command.kick")) {
            noPermission(sender, "kick players");
            return;
        }
        Player targetPlayer = Minetweak.getPlayerByName(args[0]);

        if (Minetweak.isPlayerOnline(args[0])) {
            sender.sendMessage("You cannot kick an offline player.");
        } else {
            if (args.length == 1) {
                targetPlayer.kickPlayer();
            } else if (args.length >= 2) {
                args = StringUtils.dropFirstString(args);
                String reason = mergeArgs(args);
                targetPlayer.kickPlayer(reason);
            }
        }
    }

}
