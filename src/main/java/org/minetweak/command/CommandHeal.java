package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.TextColor;
import org.minetweak.entity.Player;

public class CommandHeal extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!sender.hasPermission("minetweak.command.heal")) {
            noPermission(sender, "heal players");
            return;
        }
        if (!sender.isPlayer()) {
            notPlayer(sender);
            return;
        }
        Player player = (Player) sender;
        if (args.length>1) {
            sender.sendMessage("Usage: /heal [player]");
        } else if (args.length==1) {
            player = Minetweak.getPlayerByName(args[0]);
            if (player==null) {
                sender.sendMessage(TextColor.RED + "Can't heal an offline player.");
                return;
            }
        }
        player.setPlayerHealth(20F);
        player.setPlayerHunger(20);
        player.sendMessage(TextColor.BLUE + "You have been healed.");
    }
}
