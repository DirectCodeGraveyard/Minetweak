package org.minetweak.console;

import org.minetweak.Minetweak;
import org.minetweak.chat.TextColor;
import org.minetweak.command.CommandSender;

import java.util.List;

/**
 * Represents the Console
 */
public class Console implements CommandSender {

    @Override
    public void sendMessage(String message) {
        Minetweak.info(cleanMessage(message));
    }

    @Override
    public void sendMessage(String[] messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    @Override
    public void sendMessage(List<String> messages) {
        sendMessage(messages.toArray(new String[messages.size()]));
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
    public boolean isPlayer() {
        return false;
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    public static String cleanMessage(String message) {
        for (String s : TextColor.getColorNodes()) {
            message = message.replace(s, "");
        }
        return message;
    }
}
