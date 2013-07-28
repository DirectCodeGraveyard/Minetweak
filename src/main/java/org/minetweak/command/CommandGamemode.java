package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.console.Console;
import org.minetweak.entity.Player;
import org.minetweak.server.GameMode;
import org.minetweak.util.StringUtils;

import java.util.ArrayList;

public class CommandGamemode extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 1 && args.length != 2) {
            sender.sendMessage("Usage: /gamemode <gamemode> [player]");
            return;
        } else if (!sender.hasPermission("minetweak.command.gamemode")) {
            noPermission(sender, "change gamemodes");
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

            Player player = Minetweak.getPlayerByName(sender.getName().toLowerCase());

            if (gamemode == 0) player.setGameMode(GameMode.SURVIVAL);
            if (gamemode == 1) player.setGameMode(GameMode.CREATIVE);
            if (gamemode == 2) player.setGameMode(GameMode.ADVENTURE);
        } else {
            Player player = Minetweak.getPlayerByName(args[1].toLowerCase());

            if (gamemode == 0) player.setGameMode(GameMode.SURVIVAL);
            if (gamemode == 1) player.setGameMode(GameMode.CREATIVE);
            if (gamemode == 2) player.setGameMode(GameMode.ADVENTURE);
        }
    }

    @Override
    public String getHelpInfo() {
        return "Changes a Players Gamemode";
    }

    @Override
    public void getTabCompletion(CommandSender sender, String input, ArrayList<String> completions) {
        String newInput = input.substring(input.lastIndexOf(" "));
        if (StringUtils.isInteger(newInput)) {
            completions.addAll(Minetweak.getPlayers().keySet());
        } else {
            completions.add("0");
            completions.add("1");
            completions.add("2");
        }
    }

}
