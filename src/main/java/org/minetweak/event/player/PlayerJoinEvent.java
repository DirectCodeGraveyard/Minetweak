package org.minetweak.event.player;

import org.minetweak.entity.Player;

public class PlayerJoinEvent extends PlayerEvent {
    public PlayerJoinEvent(Player playerJoined) {
        super(playerJoined);
    }
}
