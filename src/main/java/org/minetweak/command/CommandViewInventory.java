package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.entity.Player;

public class CommandViewInventory extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!sender.hasPermission("minetweak.command.viewinv")) {
            noPermission(sender, "view inventories");
            return;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players can use this command.");
            return;
        }
        if (args.length < 1) {
            sender.sendMessage("Usage: /" + overallCommand + " player");
            return;
        }
        Player target = Minetweak.getPlayerByName(args[0]);
        if (target == null) {
            sender.sendMessage(args[0] + " is offline.");
            return;
        }
        Player player = (Player) sender;
        player.showInventory(target.getInventory());
    }
}
