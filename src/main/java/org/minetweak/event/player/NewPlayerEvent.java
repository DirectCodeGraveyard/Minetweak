package org.minetweak.event.player;

import org.minetweak.entity.Player;

/**
 * Dispatched when a player joins that has never joined before.
 */
public class NewPlayerEvent extends PlayerEvent {
    /**
     * Creates a New Player Event
     * @param player new player
     */
    public NewPlayerEvent(Player player) {
        super(player);
    }
}
