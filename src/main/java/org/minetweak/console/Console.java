package org.minetweak.console;

import org.minetweak.Minetweak;
import org.minetweak.chat.TextColor;
import org.minetweak.command.CommandSender;

public class Console implements CommandSender {

    @Override
    public void sendMessage(String message) {
        for (String s : TextColor.getColorNodes()) {
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
    public boolean isPlayer() {
        return false;
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }
}
