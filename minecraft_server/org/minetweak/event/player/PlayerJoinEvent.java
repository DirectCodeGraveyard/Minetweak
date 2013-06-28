package org.minetweak.event.player;

import org.minetweak.entity.Player;

public class PlayerJoinEvent {

    private Player playerJoined;

    public PlayerJoinEvent(Player playerJoined) {
        this.playerJoined = playerJoined;
    }

    public Player getPlayer() {
        return playerJoined;
    }

}
