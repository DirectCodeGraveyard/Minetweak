package org.minetweak.command;

import net.minecraft.server.MinecraftServer;
import org.minetweak.Server;
import org.minetweak.chat.ChatFormatting;
import org.minetweak.permissions.PlayerWhitelist;

import java.util.ArrayList;

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
                ArrayList<String> players = PlayerWhitelist.getWhitelistedPlayers();
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
                MinecraftServer.getServer().getConfigurationManager().addToWhiteList(args[1]);
                Server.sendToOps("Added " + args[1] + " to the whitelist.", true);
                return;
            }

            if (args[0].equals("remove")) {
                if (args.length < 2) {
                    sender.sendMessage(getUsage());
                    return;
                }

                MinecraftServer.getServer().getConfigurationManager().removeFromWhitelist(args[1]);
                Server.sendToOps("Removed " + args[1] + " from whitelist.", true);
                return;
            }

            if (args[0].equals("reload")) {
                MinecraftServer.getServer().getConfigurationManager().loadWhiteList();
                Server.sendToOps("Loaded Whitelist.", true);
            }
        }
    }

    public String getUsage() {
        return "Usage: /whitelist add/remove/on/off/reload [add/remove:player]";
    }
}
