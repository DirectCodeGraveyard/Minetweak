package org.minetweak.command;

import org.minetweak.server.Server;

public class CommandStop extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 0) {
            sender.sendMessage("Usage: /stop");
        } else if (!sender.hasPermission("minetweak.command.stop")) {
            sender.sendMessage("You don't have the permissions to do that.");
            return;
        }

        Server.broadcastMessage("Stopping the server...");
        Server.shutdownServer();
    }

    @Override
    public String getHelpInfo() {
        return "Stop the server";
    }

}
