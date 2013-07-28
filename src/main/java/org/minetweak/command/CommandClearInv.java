package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.TabCompletion;
import org.minetweak.entity.Player;

import java.util.ArrayList;

public class CommandClearInv extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        String username;
        if (!sender.hasPermission("minetweak.command.clearinv")) {
            noPermission(sender, "clear inventories");
            return;
        }
        if (args.length >= 1) {
            username = args[0].toLowerCase();
        } else {
            username = sender.getName();
        }
        Player player = Minetweak.getPlayerByName(username);
        player.getInventory().clear();
        sender.sendMessage("You have cleared your inventory.");
    }

    @Override
    public String getHelpInfo() {
        return "Clears Inventory";
    }

    @Override
    public void getTabCompletion(CommandSender sender, String input, ArrayList<String> completions) {
        TabCompletion.getPlayersOnlyCommand(input, completions);
    }
}
