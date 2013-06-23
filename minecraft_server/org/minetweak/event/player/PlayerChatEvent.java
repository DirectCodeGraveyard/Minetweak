package org.minetweak.event.player;

import org.minetweak.entity.Player;

public class PlayerChatEvent extends PlayerEvent {

    private String message;

    public PlayerChatEvent(Player player, String message) {
        super(player);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
