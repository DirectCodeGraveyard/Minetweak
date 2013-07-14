package org.minetweak.command;

public interface CommandSender {

    public void sendMessage(String message);

    public void sendMessage(String[] messages);

    public String getName();

    public boolean isKickable();

    public boolean hasPermission(String permission);
}
