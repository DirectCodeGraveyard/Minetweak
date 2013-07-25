package org.minetweak.event.player;

import org.minetweak.entity.Player;

public class PlayerEvent {
    private Player player;

    /**
     * Creates a Player Event
     *
     * @param player player involved
     */
    public PlayerEvent(Player player) {
        this.player = player;
    }

    /**
     * Gets the Player Involved in the Event
     *
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }
}
