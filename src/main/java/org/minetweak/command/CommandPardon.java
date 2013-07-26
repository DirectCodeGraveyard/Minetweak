package org.minetweak.command;

import org.minetweak.Server;

public class CommandPardon extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /ban <player> [reason]");
            return;
        } else if (!sender.hasPermission("minetweak.command.pardon")) {
            noPermission(sender, "pardon banned players");
            return;
        }
        Server.pardonPlayer(args[0]);
    }

    @Override
    public String getHelpInfo() {
        return "Un-bans a player";
    }
}
