package org.minetweak.event.player;

import org.minetweak.Minetweak;
import org.minetweak.entity.Player;

public class PlayerChatEvent {

    private String username;
    private String message;

    public PlayerChatEvent(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public Player getPlayer() {
        return Minetweak.getPlayerByName(username);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String newMessage) {
        message = newMessage;
    }

}
