package org.minetweak;

import net.minecraft.entity.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.utils.chat.ChatMessageComponent;
import org.minetweak.chat.ChatFormatting;
import org.minetweak.command.Console;
import org.minetweak.config.MinetweakConfig;
import org.minetweak.entity.Player;
import org.minetweak.permissions.ServerOps;
import org.minetweak.util.StringUtils;

/**
 * This is a class for managing the MinecraftServer
 */
public class Server {

    public static boolean broadcastMessage(String message) {
        if (Minetweak.isServerDoneLoading()) {
            MinecraftServer.getServer().getConfigurationManager().sendChatMessageToAll(ChatMessageComponent.func_111077_e(String.format("[%s%s%s] %s", ChatFormatting.GOLD, "Server", ChatFormatting.RESET, message)));
            return true;
        } else {
            return false;
        }
    }

    public static boolean kickPlayer(String playerName) {
        Player targetPlayer = Minetweak.getPlayerByName(playerName);
        if (targetPlayer == null) return false;
        targetPlayer.kickPlayer();
        return true;
    }

    public static boolean kickPlayer(String playerName, String kickReason) {
        Player targetPlayer = Minetweak.getPlayerByName(playerName);
        if (targetPlayer == null) return false;
        targetPlayer.kickPlayer(kickReason);
        return true;
    }

    public static boolean banPlayer(String playerName) {
        Player targetPlayer = Minetweak.getPlayerByName(playerName);
        if (targetPlayer == null) return false;
        targetPlayer.banPlayer();
        return true;
    }

    public static boolean banPlayer(String playerName, String banReason) {
        Player targetPlayer = Minetweak.getPlayerByName(playerName);
        if (targetPlayer == null) return false;
        targetPlayer.kickPlayer(banReason);
        return true;
    }

    public static void shutdownServer() {
        MinecraftServer.getServer().initiateShutdown();
        MinecraftServer.getServer().stopServer();
    }

    public static void handleCommand(EntityPlayerMP player, String command) {
        if (command.startsWith("/")) {
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
            player.addChatMessage(ChatFormatting.RED + "No Such Command: " + commandOnly);
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
            console.sendMessage(ChatFormatting.RED + "No Such Command: " + commandOnly);
        }
    }

    public static void opPlayer(String playerUsername) {
        ServerOps.addOp(playerUsername);
    }

    public static void deopPlayer(String playerUsername) {
        ServerOps.removeOp(playerUsername);
    }


    public static void pardonPlayer(String playerUsername) {
        MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().remove(playerUsername);
    }

    public static boolean isWhitelistEnabled() {
        return MinetweakConfig.getBoolean("server.whitelist-enabled");
    }

    /**
     * Send a message to all operators on the server.
     * @param message Message to send.
     */
    public static void sendToOps(String message) {
        String out = "[" + ChatFormatting.GOLD + "Server" + ChatFormatting.RESET + "] " + message;

        if (ServerOps.getOps().size() == 0) return;

        for (String op : ServerOps.getOps()) {
            if (Minetweak.isPlayerOnline(op)) {
                Player player = Minetweak.getPlayerByName(op);
                player.sendMessage(out);
            }
        }
        Minetweak.info(message);
    }
}
