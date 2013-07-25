package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.ChatFormatting;

public class Console implements CommandSender {

    @Override
    public void sendMessage(String message) {
        for (String s : ChatFormatting.getColorNodes()) {
            message = message.replace(s, "");
        }
        Minetweak.info(message);
    }

    @Override
    public void sendMessage(String[] messages) {
        for (String message : messages) {
            sendMessage(message);
        }
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
