package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.Server;
import org.minetweak.chat.TabCompletion;
import org.minetweak.entity.Player;

import java.util.ArrayList;

public class CommandOp extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /op <player>");
            return;
        } else if (!sender.hasPermission("minetweak.command.op")) {
            noPermission(sender, "op players");
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

    @Override
    public String getHelpInfo() {
        return "Ops a Player";
    }

    @Override
    public void getTabCompletion(CommandSender sender, String input, ArrayList<String> completions) {
        TabCompletion.getPlayersOnlyCommand(input, completions);
    }
}
