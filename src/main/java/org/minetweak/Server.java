package org.minetweak;

import net.minecraft.entity.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.utils.chat.ChatMessageComponent;
import net.minecraft.utils.enums.EnumChatFormatting;
import org.minetweak.chat.ChatFormatting;
import org.minetweak.command.Console;
import org.minetweak.config.MinetweakConfig;
import org.minetweak.entity.Player;
import org.minetweak.util.StringUtils;

@SuppressWarnings("UnusedDeclaration")
/**
 * This is a class for managing the MinecraftServer
 */
public class Server {

    public static boolean broadcastMessage(String message) {
        if (Minetweak.isServerDoneLoading()) {
            MinecraftServer.getServer().getConfigurationManager().sendChatMessageToAll(ChatMessageComponent.func_111077_e(String.format("[%s%s%s] %s", EnumChatFormatting.GOLD, "Server", EnumChatFormatting.RESET, message)));
            return true;
        } else {
            return false;
        }
    }

    public static boolean kickPlayer(String playerName) {
        Player targetPlayer = Minetweak.getPlayerByName(playerName);
        if(targetPlayer == null) return false;
        targetPlayer.kickPlayer();
        return true;
    }

    public static boolean kickPlayer(String playerName, String kickReason) {
        Player targetPlayer = Minetweak.getPlayerByName(playerName);
        if(targetPlayer == null) return false;
        targetPlayer.kickPlayer(kickReason);
        return true;
    }

    public static boolean banPlayer(String playerName) {
        Player targetPlayer = Minetweak.getPlayerByName(playerName);
        if(targetPlayer == null) return false;
        targetPlayer.banPlayer();
        return true;
    }

    public static boolean banPlayer(String playerName, String banReason) {
        Player targetPlayer = Minetweak.getPlayerByName(playerName);
        if(targetPlayer == null) return false;
        targetPlayer.kickPlayer(banReason);
        return true;
    }

    public static void shutdownServer() {
        MinecraftServer.getServer().initiateShutdown();
        MinecraftServer.getServer().stopServer();
    }

    public static void handleCommand(EntityPlayerMP player, String command) {
        if (command.startsWith("/"))
        {
            command = command.substring(1);
        } else {
            return;
        }

        String[] commandWithArgs = command.split(" ");
        String commandOnly = commandWithArgs[0];
        String[] args = StringUtils.dropFirstString(commandWithArgs);

        if (Minetweak.doesCommandExist(commandWithArgs[0])) {
            Minetweak.getCommandByName(commandWithArgs[0]).executeCommand(Minetweak.getPlayerByName(player.getEntityName()), commandOnly, args);
        } else {
            player.addChatMessage(EnumChatFormatting.RED + "No Such Command: " + commandOnly);
        }
    }

    public static void handleCommand(Console console, String command) {
        if (command.startsWith("/")) {
            command = command.substring(1);
        }

        String[] commandWithArgs = command.split(" ");
        String commandOnly = commandWithArgs[0];
        String[] args = StringUtils.dropFirstString(commandWithArgs);

        if (Minetweak.doesCommandExist(commandWithArgs[0])) {
            Minetweak.getCommandByName(commandWithArgs[0]).executeCommand(console, commandOnly, args);
        } else {
            console.sendMessage(EnumChatFormatting.RED + "No Such Command: " + commandOnly);
        }
    }

    public static void opPlayer(String playerUsername) {
        MinecraftServer.getServer().getConfigurationManager().addOp(playerUsername);
    }

    public static void deopPlayer(String playerUsername) {
        MinecraftServer.getServer().getConfigurationManager().removeOp(playerUsername);
    }


    public static void pardonPlayer(String playerUsername) {
        MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().remove(playerUsername);
    }

    public static boolean isWhitelistEnabled() {
        return MinetweakConfig.getBoolean("server.whitelist-enabled");
    }

    public static void sendToOps(String message) {
        sendToOps(message, false);
    }

    public static void sendToOps(String message, boolean pretty) {
        String out = message;
        if (pretty) {
            out = "[" + ChatFormatting.GOLD + "Server" + ChatFormatting.RESET + "] " + message;
        }

        for (String op : MinecraftServer.getServer().getConfigurationManager().getOps()) {
            Player player = Minetweak.getPlayerByName(op);
            if (player==null) {
                continue;
            }
            player.sendMessage(out);
        }
        Minetweak.info(message);
    }
}
