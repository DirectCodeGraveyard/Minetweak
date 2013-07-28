package org.minetweak.command;

import net.minecraft.server.MinecraftServer;
import org.minetweak.Minetweak;
import org.minetweak.chat.ChatColors;
import org.minetweak.chat.TabCompletion;
import org.minetweak.entity.Player;
import org.minetweak.permissions.PlayerWhitelist;
import org.minetweak.permissions.ServerOps;
import org.minetweak.server.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class CommandWhitelist extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length >= 1) {
            if (args[0].equals("on")) {
                MinecraftServer.getServer().getConfigurationManager().setWhiteListEnabled(true);
                Server.sendToOps("Whitelist has been enabled.");
                if (sender.isPlayer() && !(((Player) sender).isOperator())) {
                    sender.sendMessage(ChatColors.GOLD + "Whitelist has been enabled.");
                }
                return;
            }

            if (args[0].equals("off")) {
                MinecraftServer.getServer().getConfigurationManager().setWhiteListEnabled(false);
                Server.sendToOps("Whitelist Has been disabled.");
                if (sender.isPlayer() && !(((Player) sender).isOperator())) {
                    sender.sendMessage(ChatColors.GOLD + "Whitelist has been disabled.");
                }
                return;
            }

            if (args[0].equals("list")) {
                ServerOps.load();
                Set<String> players = PlayerWhitelist.getWhitelistedPlayers();
                sender.sendMessage(ChatColors.GOLD + "Whitelisted Players" + ChatColors.RESET + ":");
                boolean flag = false;
                String line = "";
                for (String player : players) {
                    if (flag) {
                        sender.sendMessage(line + " " + player);
                        flag = false;
                    } else {
                        line = ChatColors.BLUE + player;
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
                if (sender.isPlayer() && !(((Player) sender).isOperator())) {
                    sender.sendMessage(ChatColors.GOLD + "Added " + ChatColors.GREEN + args[1] + ChatColors.GOLD + " to the whitelist.");
                }
                Server.sendToOps("Added " + args[1] + " to the whitelist.");
                return;
            }

            if (args[0].equals("remove")) {
                if (args.length < 2) {
                    sender.sendMessage(getUsage());
                    return;
                }
                PlayerWhitelist.removePlayer(args[1]);
                if (sender.isPlayer() && !(((Player) sender).isOperator())) {
                    sender.sendMessage(ChatColors.GOLD + "Removed " + ChatColors.GREEN + args[1] + ChatColors.GOLD + " from the whitelist.");
                }
                Server.sendToOps("Removed " + args[1] + " from whitelist.");
                return;
            }

            if (args[0].equals("reload")) {
                PlayerWhitelist.load();
                if (sender.isPlayer() && !(((Player) sender).isOperator())) {
                    sender.sendMessage(ChatColors.GOLD + "Reloaded Whitelist");
                }
                Server.sendToOps("Loaded Whitelist.");
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
        String cmd;
        switch (length) {
            case 1:
                completions.addAll(Arrays.asList("add", "remove", "reload"));
                if (PlayerWhitelist.isWhitelistEnabled()) {
                    completions.add("off");
                } else {
                    completions.add("on");
                }
                return;
            case 2:
                cmd = split[1];
                if (cmd.equals("add")) {
                    for (String player : players) {
                        if (!PlayerWhitelist.getWhitelistedPlayers().contains(player)) {
                            completions.add(player);
                        }
                    }
                } else if (cmd.equals("remove")) {
                    completions.addAll(PlayerWhitelist.getWhitelistedPlayers());
                } else if (!cmd.equals("on") && !cmd.equals("off") && !cmd.equals("reload")) {
                    for (String command : Arrays.asList("off", "on", "add", "remove", "reload")) {
                        if (command.startsWith(cmd)) {
                            completions.add(command);
                        }
                    }
                }
                return;
            case 3:
                cmd = split[1];
                String user = split[2];
                if (cmd.equals("add")) {
                    for (String player : players) {
                        if (!PlayerWhitelist.getWhitelistedPlayers().contains(player)) {
                            completions.addAll(TabCompletion.getPlayersMatching(user));
                        }
                    }
                } else if (cmd.equals("remove")) {
                    Set<String> whitelisted = PlayerWhitelist.getWhitelistedPlayers();
                    for (String player : whitelisted) {
                        if (player.startsWith(user)) {
                            completions.add(player);
                        }
                    }
                }
        }
    }
}
