package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.Server;
import org.minetweak.entity.Player;

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

}
