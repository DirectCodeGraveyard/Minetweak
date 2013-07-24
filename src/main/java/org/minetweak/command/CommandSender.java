package org.minetweak.command;

import net.minecraft.utils.chat.ChatMessageComponent;

public interface CommandSender {

    public void sendMessage(String message);

    public void sendMessage(String[] messages);

    public String getName();

    public boolean isKickable();

    public boolean hasPermission(String permission);
}
