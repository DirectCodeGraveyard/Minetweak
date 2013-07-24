package org.minetweak.command;

import org.minetweak.Server;

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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Server.shutdownServer();
    }

    @Override
    public String getHelpInfo() {
        return "Stops the Server";
    }

}
