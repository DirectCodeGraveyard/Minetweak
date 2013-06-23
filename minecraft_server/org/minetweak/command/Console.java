package org.minetweak.command;

import net.minecraft.server.MinecraftServer;

public class Console implements CommandSender {


    @Override
    public void sendMessage(String message) {
        MinecraftServer.getServer().logInfo(message);
    }

    @Override
    public String getName() {
        return "SERVER";
    }
}
