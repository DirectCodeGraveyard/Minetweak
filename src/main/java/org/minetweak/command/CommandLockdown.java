package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.Server;

/**
 * Command that will set the server under lockdown and
 * not allow anyone but ops in, but allow players online
 * to stay on.
 */
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
