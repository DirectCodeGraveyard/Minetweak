package org.minetweak.command;

import net.minecraft.server.MinecraftServer;
import org.minetweak.Server;
import org.minetweak.chat.TabCompletion;

import java.util.ArrayList;
import java.util.Set;

public class CommandPardon extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /ban <player> [reason]");
            return;
        } else if (!sender.hasPermission("minetweak.command.pardon")) {
            noPermission(sender, "pardon banned players");
            return;
        }
        Server.pardonPlayer(args[0]);
    }

    @Override
    public String getHelpInfo() {
        return "Un-bans a player";
    }

    @Override
    public void getTabCompletion(CommandSender sender, String input, ArrayList<String> completions) {
        String[] split = input.split(" ");
        Set<String> bans = MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getBannedList().keySet();

        switch (split.length) {
            case 1:
                completions.addAll(bans);
            case 2:
                completions.addAll(TabCompletion.getPlayersMatching(split[1]));
        }
    }
}
