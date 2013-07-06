package org.minetweak.command;

import org.minetweak.permissions.PermissionsLoader;

public class CommandLoadData extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        sender.sendMessage("Loading Permissions");
        PermissionsLoader.load();
    }
}
