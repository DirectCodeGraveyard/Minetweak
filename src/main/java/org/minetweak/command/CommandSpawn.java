package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.TextColor;
import org.minetweak.entity.Player;

public class CommandSpawn extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        Player player;
        if (sender.hasPermission("minetweak.command.spawn")) {
            if (args.length > 1) {
                sender.sendMessage("Usage: /" + overallCommand + " [player]");
                return;
            } else if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player to use this command.");
                return;
            }
            String playerName = sender.getName();
            if (args.length == 1) {
                playerName = args[0];
            }
            player = Minetweak.getPlayerByName(playerName);
            if (player == null) {
                sender.sendMessage(TextColor.RED + "Player does not exist.");
                return;
            }
        } else {
            noPermission(sender, "teleport players to spawn");
            return;
        }
        player.sendMessage(TextColor.GREEN + "Teleporting to Spawn");
        player.teleportToPosition(player.getCurrentWorld().getSpawn());
    }

    @Override
    public String getHelpInfo() {
        return "Teleports a player to Spawn";
    }
}
