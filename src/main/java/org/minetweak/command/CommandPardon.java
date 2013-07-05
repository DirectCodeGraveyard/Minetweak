package org.minetweak.command;

import org.minetweak.Server;

public class CommandPardon extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /ban <player> [reason]");
            return;
        } else if (!sender.hasPermission("minetweak.command.pardon")) {
            sender.sendMessage("You don't have the permissions to do that.");
            return;
        }
        Server.pardonPlayer(args[1]);
    }
}
