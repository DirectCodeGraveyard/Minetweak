package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.TabCompletion;
import org.minetweak.chat.TextColor;
import org.minetweak.entity.Player;

import java.util.ArrayList;

public class CommandTeleport extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length > 2 || args.length == 0) {
            sender.sendMessage(getUsage());
            return;
        }
        sender.sendMessage("Teleporting....");
        Player player;
        Player target;
        if (args.length == 1) {
            player = Minetweak.getPlayerByName(sender.getName());
            target = Minetweak.getPlayerByName(args[0].toLowerCase());
        } else {
            player = Minetweak.getPlayerByName(args[0].toLowerCase());
            target = Minetweak.getPlayerByName(args[1].toLowerCase());
        }
        if (player == null) {
            sender.sendMessage(TextColor.RED + args[0].toLowerCase() + " is not online.");
            return;
        }
        if (target == null) {
            sender.sendMessage(TextColor.RED + args[1].toLowerCase() + " is not online.");
            return;
        }
        sender.sendMessage(TextColor.GOLD + "Teleporting " + TextColor.BLUE + player.getName() + TextColor.RESET + " to " + TextColor.BLUE + target.getName());
        player.teleportToPosition(target.getX(), target.getY(), target.getZ());
    }

    public String getUsage() {
        return "Usage: /tp [player] [target-player]";
    }

    @Override
    public String getHelpInfo() {
        return "Teleports a Player to another Player";
    }

    @Override
    public void getTabCompletion(CommandSender sender, String input, ArrayList<String> completions) {
        int length = input.split(" ").length;

        switch (length) {
            case 1:
                completions.addAll(TabCompletion.getPlayersMatching(input));
            case 2:
                completions.addAll(TabCompletion.getPlayersMatching(input));
        }
    }
}
