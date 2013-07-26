package org.minetweak.command;

import net.minecraft.server.MinecraftServer;
import org.minetweak.Minetweak;
import org.minetweak.Server;
import org.minetweak.chat.ChatFormatting;
import org.minetweak.permissions.PlayerWhitelist;
import org.minetweak.permissions.ServerOps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class CommandWhitelist extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length >= 1) {
            if (args[0].equals("on")) {
                MinecraftServer.getServer().getConfigurationManager().setWhiteListEnabled(true);
                Server.sendToOps("Whitelist has been enabled.", true);
                return;
            }

            if (args[0].equals("off")) {
                MinecraftServer.getServer().getConfigurationManager().setWhiteListEnabled(false);
                Server.sendToOps("Whitelist Has been disabled.", true);
                return;
            }

            if (args[0].equals("list")) {
                ServerOps.load();
                Set<String> players = PlayerWhitelist.getWhitelistedPlayers();
                sender.sendMessage(ChatFormatting.GOLD + "Whitelisted Players" + ChatFormatting.RESET + ":");
                boolean flag = false;
                String line = "";
                for (String player : players) {
                    if (flag) {
                        sender.sendMessage(line + " " + player);
                        flag = false;
                    } else {
                        line = ChatFormatting.BLUE + player;
                        flag = true;
                    }
                }
                return;
            }

            if (args[0].equals("add")) {
                if (args.length < 2) {
                    sender.sendMessage(getUsage());
                    return;
                }
                PlayerWhitelist.addPlayer(args[1]);
                Server.sendToOps("Added " + args[1] + " to the whitelist.", true);
                return;
            }

            if (args[0].equals("remove")) {
                if (args.length < 2) {
                    sender.sendMessage(getUsage());
                    return;
                }

                PlayerWhitelist.removePlayer(args[1]);
                Server.sendToOps("Removed " + args[1] + " from whitelist.", true);
                return;
            }

            if (args[0].equals("reload")) {
                PlayerWhitelist.load();
                Server.sendToOps("Loaded Whitelist.", true);
                return;
            }
        }
        sender.sendMessage(getUsage());
    }

    public String getUsage() {
        return "Usage: /whitelist add/remove/on/off/reload [add/remove:player]";
    }

    @Override
    public String getHelpInfo() {
        return "Manages the Whitelist";
    }

    @Override
    public void getTabCompletion(CommandSender sender, String input, ArrayList<String> completions) {
        String[] split = input.split(" ");
        int length = split.length;
        Set<String> players = Minetweak.getPlayers().keySet();

        switch (length) {
            case 1:
                completions.addAll(Arrays.asList("add", "remove", "reload"));
            case 2:
                String cmd = split[1];
                if (cmd.equals("add")) {
                    for (String player : players) {
                        if (!PlayerWhitelist.getWhitelistedPlayers().contains(player)) {
                            completions.add(player);
                        }
                    }
                } else if (cmd.equals("remove")) {
                    completions.addAll(PlayerWhitelist.getWhitelistedPlayers());
                }
        }
    }
}
