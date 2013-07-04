package org.minetweak.command;

import net.minecraft.server.MinecraftServer;

public class Console implements CommandSender {

    @Override
    public void sendMessage(String message) {
        MinecraftServer.getServer().logInfo(message);
    }

    @Override
    public String getName() {
        return "Server";
    }

    @Override
    public boolean isKickable() {
        return false;
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }
}
