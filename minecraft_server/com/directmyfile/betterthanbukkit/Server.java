package com.directmyfile.betterthanbukkit;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;

public class Server {

    /**
     * Broadcast a message to the server, this allows you to tell everyone something at the same time easily
     * @param message The message you want to broadcast to every player and to the console
     * @return if the message was properly broadcasted
     */
    public static boolean broadcastMessage(String message) {
        if (BetterThanBukkit.isServerDoneLoading()) {
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
            return false;
        } else {
            targetPlayer.playerNetServerHandler.kickPlayer("You have been kicked from the server.");
            return true;
        }
    }

    /**
     * Kick a player with a custom kick message
     * @param playerName Player's name
     * @param kickMessage Custom kick message
     * @return true on successful kick
     */
    public static boolean kickPlayer(String playerName, String kickMessage) {
        EntityPlayerMP targetPlayer = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(playerName);
        if (targetPlayer == null) {
            return false;
        } else {
            targetPlayer.playerNetServerHandler.kickPlayer("You have been kicked from the server.");
            return true;
        }
    }

}
