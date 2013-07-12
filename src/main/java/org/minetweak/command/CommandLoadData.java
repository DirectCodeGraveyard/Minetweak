package org.minetweak.command;

import org.minetweak.permissions.PermissionsLoader;

public class CommandLoadData extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 0) {
            sender.sendMessage("Usage: /");
            return;
        }
        sender.sendMessage("Loading Permissions");
        PermissionsLoader.load();
    }
}
