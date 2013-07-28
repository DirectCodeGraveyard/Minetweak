package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.entity.Player;
import org.minetweak.util.StringUtils;

import java.util.ArrayList;

public class CommandBan extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /ban <player> [reason]");
            return;
        } else if (!sender.hasPermission("minetweak.command.ban")) {
            noPermission(sender, "ban players");
            return;
        }
        Player targetPlayer = Minetweak.getPlayerByName(args[0].toLowerCase());
        if (args.length == 1) {
            targetPlayer.banPlayer();
        } else if (args.length >= 2) {
            args = StringUtils.dropFirstString(args);
            String reason = mergeArgs(args);
            targetPlayer.banPlayer(reason);
        }
    }

    @Override
    public String getHelpInfo() {
        return "Bans a Player";
    }

    @Override
    public void getTabCompletion(CommandSender sender, String input, ArrayList<String> completions) {
        int length = input.split(" ").length;

        switch (length) {
            case 1:
                completions.addAll(Minetweak.getPlayers().keySet());
        }
    }

}
