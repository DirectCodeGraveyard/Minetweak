package org.minetweak.command;

import org.minetweak.Server;

public class CommandSay extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /say <message>");
            return;
        } else if (!sender.hasPermission("minetweak.command.say")) {
            sender.sendMessage("You don't have the permissions to do that.");
            return;
        }

        String message = mergeArgs(args);
        Server.broadcastMessage(message);
    }
}
