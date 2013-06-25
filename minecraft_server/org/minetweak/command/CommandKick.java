package org.minetweak.command;

import net.minecraft.src.EnumChatFormatting;
import org.minetweak.Minetweak;
import org.minetweak.entity.Player;

public class CommandKick extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {

        System.out.println(overallCommand);

        for (String s : args) {
            System.out.println(s + " ");
        }

        if (args.length == 0) {
            sender.sendMessage(EnumChatFormatting.AQUA + "Usage: /kick <player> [reason]");
            return;
        }
        Player targetPlayer = Minetweak.getPlayerByName(args[0]);
        if (args.length == 1) {
            targetPlayer.kickPlayer();
        } else if (args.length <= 2) {
            //TODO: Ask Sam wtf is going on here.
            String reason = createString(args, 1);
            System.out.println(reason);
            for (String s : args) {
                System.out.println(s);
            }
            targetPlayer.kickPlayer(reason);
        }
    }

}
