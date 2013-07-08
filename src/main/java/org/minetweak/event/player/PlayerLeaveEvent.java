package org.minetweak.event.player;

import org.minetweak.entity.Player;

public class PlayerLeaveEvent extends PlayerEvent {
    /**
     * Creates a Player Leave Event
     * @param player player involved
     */
    public PlayerLeaveEvent(Player player) {
        super(player);
    }
}
