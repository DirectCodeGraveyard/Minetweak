package org.minetweak;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ChatMessageComponent;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EnumChatFormatting;
import org.minetweak.command.Console;
import org.minetweak.entity.Player;
import org.minetweak.util.StringUtils;

@SuppressWarnings("UnusedDeclaration")
public class Server {

    public static boolean broadcastMessage(String message) {
        if (Minetweak.isServerDoneLoading()) {
            MinecraftServer.getServer().getConfigurationManager().sendChatToAllPlayers(ChatMessageComponent.func_111077_e(String.format("[%s] %s", "Server", message)), true);
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
}
