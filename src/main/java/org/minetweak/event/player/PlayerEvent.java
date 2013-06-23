package org.minetweak.event.player;

import org.minetweak.entity.Player;

public abstract class PlayerEvent {

    protected Player player;

    public PlayerEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
