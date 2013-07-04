package org.minetweak.command;

public interface CommandSender {

    public void sendMessage(String message);

    public String getName();

    public boolean isKickable();

    public boolean hasPermission(String permission);
}
