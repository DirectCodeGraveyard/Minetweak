package org.minetweak.command;

import net.minecraft.utils.enums.EnumGameType;
import org.minetweak.Minetweak;
import org.minetweak.entity.Player;
import org.minetweak.util.StringUtils;

public class CommandGamemode extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 1 && args.length != 2) {
            sender.sendMessage("Usage: /gamemode <gamemode> [player]");
            return;
        } else if (!sender.hasPermission("minetweak.command.gamemode")) {
            sender.sendMessage("You don't have the permissions to do that.");
            return;
        }

        int gamemode = -1;

        if (!StringUtils.isInteger(args[0])) {
            if (args[0].toLowerCase().equals("survival")) gamemode = 0;
            if (args[0].toLowerCase().equals("creative")) gamemode = 1;
            if (args[0].toLowerCase().equals("adventure")) gamemode = 2;
        } else {
            if (Integer.parseInt(args[0]) == 0) gamemode = 0;
            if (Integer.parseInt(args[0]) == 1) gamemode = 1;
            if (Integer.parseInt(args[0]) == 2) gamemode = 2;
        }

        if (args.length == 1) {
            if (sender instanceof Console) {
                sender.sendMessage("Consoles can not run this command.");
                return;
            }

            Player player = Minetweak.getPlayerByName(sender.getName());

            if (gamemode == 0) player.setGameMode(EnumGameType.SURVIVAL);
            if (gamemode == 1) player.setGameMode(EnumGameType.CREATIVE);
            if (gamemode == 2) player.setGameMode(EnumGameType.ADVENTURE);

            if (gamemode == 0) player.sendMessage("Your gamemode was set to survival");
            if (gamemode == 1) player.sendMessage("Your gamemode was set to creative");
            if (gamemode == 2) player.sendMessage("Your gamemode was set to adventure");
        } else {
            Player player = Minetweak.getPlayerByName(args[1]);

            if (gamemode == 0) player.setGameMode(EnumGameType.SURVIVAL);
            if (gamemode == 1) player.setGameMode(EnumGameType.CREATIVE);
            if (gamemode == 2) player.setGameMode(EnumGameType.ADVENTURE);

            if (gamemode == 0) player.sendMessage("Your gamemode was set to survival");
            if (gamemode == 1) player.sendMessage("Your gamemode was set to creative");
            if (gamemode == 2) player.sendMessage("Your gamemode was set to adventure");
        }
    }

}
