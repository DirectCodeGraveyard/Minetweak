package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.Server;

public class CommandLockdown extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!sender.hasPermission("minetweak.command.lockdown")) {
            noPermission(sender, "lockdown the server");
            return;
        }

        if (!Minetweak.isServerLockedDown()) {
            Minetweak.setLockedDown(true);
            Server.broadcastMessage("Server is now on lockdown.");
        } else {
            Minetweak.setLockedDown(false);
            Server.broadcastMessage("Server is no longer on lockdown.");
        }
    }

    @Override
    public String getHelpInfo() {
        return "Toggles the Server Lockdown";
    }
}
