package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.entity.Player;
import org.minetweak.util.StringUtils;

public class CommandGive extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!sender.hasPermission("minetweak.command.give")) {
            noPermission(sender, "give items");
            return;
        }

        if (args.length < 1 || args.length > 3) {
            sender.sendMessage("Usage: /" + overallCommand + " itemID [user] [amount]");
            return;
        }

        if (sender instanceof Console && !(args.length >= 2)) {
            sender.sendMessage("Console cannot give items to itself.");
            return;
        }

        if (args.length == 1) {
            Player player = Minetweak.getPlayerByName(sender.getName());
            if (StringUtils.isInteger(args[0])) {
                player.getPlayerMP().dropItem(Integer.parseInt(args[0]), 1);
            } else {
                sender.sendMessage(args[0] + " is not a valid item id");
            }
            return;
        }

        if (args.length == 2 && StringUtils.isInteger(args[1])) {
            Player player = Minetweak.getPlayerByName(sender.getName());
            if (StringUtils.isInteger(args[0])) {
                player.getPlayerMP().dropItem(Integer.parseInt(args[0]), 1);
            } else {
                sender.sendMessage(args[0] + " is not a valid item id");
            }
            return;
        }

        Player player = Minetweak.getPlayerByName(args[1].toLowerCase());

        if (player == null) {
            sender.sendMessage(args[1].toLowerCase() + " is not online!");
            return;
        }

        if (args.length == 2) {
            if (StringUtils.isInteger(args[0])) {
                player.getPlayerMP().dropItem(Integer.parseInt(args[0]), 1);
            } else {
                sender.sendMessage(args[0] + " is not a valid item id");
            }
            return;
        }

        if (StringUtils.isInteger(args[0]) && StringUtils.isInteger(args[2])) {
            player.getPlayerMP().dropItem(Integer.parseInt(args[0]), Integer.parseInt(args[2]));
        } else {
            sender.sendMessage(args[0] + " is not a valid item id");
        }
    }

    @Override
    public String getHelpInfo() {
        return "Gives a Player Items";
    }
}
