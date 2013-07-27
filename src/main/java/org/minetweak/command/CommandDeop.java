package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.TabCompletion;
import org.minetweak.entity.Player;
import org.minetweak.permissions.ServerOps;

import java.util.ArrayList;

public class CommandDeop extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /deop <player>");
            return;
        } else if (!sender.hasPermission("minetweak.command.deop")) {
            noPermission(sender, "deop players");
            return;
        }
        Player targetPlayer = Minetweak.getPlayerByName(args[0]);

        ServerOps.removeOp(args[0]);

        if (Minetweak.isPlayerOnline(args[0])) {
            targetPlayer.sendMessage("You have been deopped by: " + sender.getName());
            sender.sendMessage("You deopped " + args[0]);
        } else {
            sender.sendMessage("You deopped " + args[0] + ", who is offline");
        }
    }

    @Override
    public String getHelpInfo() {
        return "Deops a Player";
    }

    @Override
    public void getTabCompletion(CommandSender sender, String input, ArrayList<String> completions) {
        TabCompletion.getPlayersOnlyCommand(input, completions);
    }

}
