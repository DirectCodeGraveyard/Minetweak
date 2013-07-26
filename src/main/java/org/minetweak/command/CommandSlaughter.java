package org.minetweak.command;

import org.minetweak.chat.ChatFormatting;
import org.minetweak.entity.Mob;
import org.minetweak.entity.Player;

import java.util.ArrayList;

public class CommandSlaughter extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!(sender.hasPermission("minetweak.command.slaughter"))) {
            noPermission(sender, "kill all mobs nearby");
            return;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return;
        }
        if (args.length != 0) {
            sender.sendMessage("Usage: /" + overallCommand);
            return;
        }
        Player player = (Player) sender;
        ArrayList<Mob> mobs = player.getNearbyMobs(500);
        for (Mob mob : mobs) {
            mob.setDead();
        }
        sender.sendMessage(ChatFormatting.GOLD + "Killed " + ChatFormatting.GREEN + mobs.size() + ChatFormatting.GOLD + " mobs.");
    }

    @Override
    public String getHelpInfo() {
        return "Kills Nearby Mobs";
    }
}
