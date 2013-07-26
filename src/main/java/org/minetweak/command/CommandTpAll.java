package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.entity.Player;

public class CommandTpAll extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(getUsage());
            return;
        } else if (!sender.hasPermission("minetweak.command.tp.all")) {
            noPermission(sender, "teleport all players");
            return;
        } else if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to run this command.");
            return;
        }

        Player player = Minetweak.getPlayerByName(sender.getName());

        for (Player p : Minetweak.getPlayers().values()) {
            if (!p.getDisplayName().toLowerCase().equals(sender.getName().toLowerCase())) {
                p.teleportToPosition(player.getX(), player.getY(), player.getZ());
            }
        }
    }

    public String getUsage() {
        return "Usage: /tpall";
    }

    @Override
    public String getHelpInfo() {
        return "Teleports all Players to your position";
    }
}