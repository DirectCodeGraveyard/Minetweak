package org.minetweak.event.player;

import org.minetweak.entity.Player;

public class PlayerOpEvent extends PlayerEvent {

    private boolean isOfflinePlayer;
    private String username;

    public PlayerOpEvent(Player playerInstance) {
        super(playerInstance);
        isOfflinePlayer = false;
    }

    public PlayerOpEvent(String playerUsername) {
        super(null);
        isOfflinePlayer = true;
        this.username = playerUsername;
    }

    /**
     * Checks if the player is offline
     *
     * @return if player is offline
     */
    public boolean isOfflinePlayer() {
        return isOfflinePlayer;
    }

    /**
     * Gets player username
     *
     * @return player username
     */
    public String getPlayerUsername() {
        return username;
    }
}
