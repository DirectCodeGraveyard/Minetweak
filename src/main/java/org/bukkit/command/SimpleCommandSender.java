package org.bukkit.command;

import org.bukkit.Server;

public class SimpleCommandSender implements CommandSender {
    private org.minetweak.command.CommandSender sender;

    public SimpleCommandSender(org.minetweak.command.CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    @Override
    public String getName() {
        return sender.getName();
    }

    @Override
    public boolean isKickable() {
        return sender.isKickable();
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    public Server getServer() {
        return new Server();
    }
}
