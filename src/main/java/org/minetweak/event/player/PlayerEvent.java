package org.minetweak.event.player;

import org.minetweak.entity.Player;
import org.minetweak.event.Event;

public abstract class PlayerEvent extends Event {

    protected Player player;

    public PlayerEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
