package org.minetweak.event.player;

import org.minetweak.Minetweak;
import org.minetweak.entity.Player;

public class PlayerChatEvent extends PlayerEvent {

    private String username;
    private String message;

    public PlayerChatEvent(String username, String message) {
        super(Minetweak.getPlayerByName(username));
        this.username = username;
        this.message = message;
    }

    /**
     * Gets the username of the player
     * @return username of player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the message
     * @return message
     */
    public String getMessage() {
        return message;
    }
}
