package org.minetweak.command;

import net.minecraft.src.EnumChatFormatting;
import org.minetweak.Minetweak;
import org.minetweak.entity.Player;

public class CommandClearInv extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        String username;
        if (!sender.hasPermission("minetweak.command.clearinv")) {
            sender.sendMessage(EnumChatFormatting.RED + "You do not have permission to clear inventories!");
            return;
        }
        if (args.length>=1) {
            username = args[0];
        } else {
            username = sender.getName();
        }
        Player player = Minetweak.getPlayerByName(username);
        player.getInventory().clear();
        sender.sendMessage("You have cleared your inventory.");
    }
}