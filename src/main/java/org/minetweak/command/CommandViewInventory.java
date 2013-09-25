package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.TextColor;
import org.minetweak.entity.Player;

public class CommandViewInventory extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!sender.hasPermission("minetweak.command.viewinv")) {
            noPermission(sender, "view inventories");
            return;
        } else if (!(sender instanceof Player)) {
            sender.sendMessage("");
            return;
        } else if (args.length != 1) {
            notPlayer(sender);
            return;
        }
        Player target = Minetweak.getPlayerByName(args[0]);
        if (target == null) {
            sender.sendMessage(TextColor.RED + "Player does not exist.");
            return;
        }
        Player player = (Player) sender;
        player.showInventory(target.getInventory());
    }
}
