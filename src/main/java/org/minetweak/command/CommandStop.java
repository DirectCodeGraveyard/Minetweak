package org.minetweak.command;

import org.minetweak.Server;

public class CommandStop extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        Server.broadcastMessage("Stopping the server...");
        Server.shutdownServer();
    }

}
