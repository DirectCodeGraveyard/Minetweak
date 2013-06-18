package com.directmyfile.betterthanbukkit;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;

public class Server {

    /**
     * Broadcast a message to the server
     * @param message
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

}
