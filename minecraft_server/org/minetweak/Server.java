package org.minetweak;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.BanEntry;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;

public class Server {

    /**
     * Broadcast a message to the server, this allows you to tell everyone something at the same time easily
     * @param message The message you want to broadcast to every player and to the console
     * @return if the message was properly broadcasted
     */
    public static boolean broadcastMessage(String message) {
        if (Minetweak.isServerDoneLoading()) {
            String var3 = CommandBase.func_82361_a(null, new String[]{message}, 0, true);
            MinecraftServer.getServer().getConfigurationManager().sendChatMsg(String.format("[%s] %s", new Object[] {"SERVER", var3}));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Kick a player
     * @param playerName Player's name
     * @return true on successful kick
     */
    public static boolean kickPlayer(String playerName) {
        EntityPlayerMP targetPlayer = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(playerName);
        if (targetPlayer == null) {
            System.out.println("The target player is null!");
            return false;
        } else {
            targetPlayer.playerNetServerHandler.kickPlayer("You have been kicked from the server.");
            return true;
        }
    }

    /**
     * Kick a player with a custom kick message
     * @param playerName Player's name
     * @param kickReason Custom kick message
     * @return true on successful kick
     */
    public static boolean kickPlayer(String playerName, String kickReason) {
        EntityPlayerMP targetPlayer = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(playerName);
        if (targetPlayer == null) {
            System.out.println("The target player is null!");
            return false;
        } else {
            targetPlayer.playerNetServerHandler.kickPlayer("You have been kicked from the server.");
            return true;
        }
    }

    public static boolean banPlayer(String playerName) {
        EntityPlayerMP targetPlayer = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(playerName);
        if (targetPlayer == null) {
            System.out.println("The target player is null!");
            return false;
        } else {
            targetPlayer.playerNetServerHandler.kickPlayer("You are banned from this server.");
            BanEntry banEntry = new BanEntry(playerName);
            MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().put(banEntry);
            return true;
        }
    }

    public static boolean banPlayer(String playerName, String banReason) {
        EntityPlayerMP targetPlayer = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(playerName);
        if (targetPlayer == null) {
            System.out.println("The target player is null!");
            return false;
        } else {
            targetPlayer.playerNetServerHandler.kickPlayer(banReason);
            BanEntry banEntry = new BanEntry(playerName);
            MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().put(banEntry);
            return true;
        }
    }

    public static void shutdownServer() {
        MinecraftServer.getServer().initiateShutdown();
    }

}
