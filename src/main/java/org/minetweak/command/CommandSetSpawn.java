package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.TextColor;
import org.minetweak.entity.Player;
import org.minetweak.world.Location;

public class CommandSetSpawn extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!sender.hasPermission("minetweak.command.setspawn")) {
            noPermission(sender, "set the server spawn");
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player to use this command.");
                return;
            }
            Player player = (Player) sender;
            Minetweak.getOverworld().setSpawn(new Location((int) player.getX(), (int) player.getY(), (int) player.getZ()));
            sender.sendMessage(TextColor.GOLD + "Spawn Location Set.");
        }
    }

    @Override
    public String getHelpInfo() {
        return "Sets the Worlds Spawn";
    }
}
