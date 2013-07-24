package org.minetweak.event.player;

import org.minetweak.entity.Player;

public class PlayerChatEvent extends PlayerEvent {

    private String message;

    public PlayerChatEvent(Player player, String message) {
        super(player);
        this.message = message;
    }

    /**
     * Gets the message
     * @return message
     */
    public String getMessage() {
        return message;
    }
}
