package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.TabCompletion;
import org.minetweak.chat.TextColor;
import org.minetweak.entity.Player;
import org.minetweak.permissions.ServerOps;

import java.util.ArrayList;

/**
 * Used to deop players, whether offline or online.
 */
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
        Player targetPlayer = Minetweak.getPlayerByName(args[0].toLowerCase());

        ServerOps.removeOp(args[0].toLowerCase());

        targetPlayer.sendMessage(TextColor.GREEN + "You are no longer op!");
        sender.sendMessage(TextColor.GREEN + "You deopped " + args[0]);
    }

    @Override
    public String getHelpInfo() {
        return "Deop a player";
    }

    @Override
    public void getTabCompletion(CommandSender sender, String input, ArrayList<String> completions) {
        TabCompletion.getPlayersOnlyCommand(input, completions);
    }

}
