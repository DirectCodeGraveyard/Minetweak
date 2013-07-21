package org.minetweak.command;

import org.minetweak.Minetweak;

public class Console implements CommandSender {

    @Override
    public void sendMessage(String message) {
        Minetweak.info(message  );
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
