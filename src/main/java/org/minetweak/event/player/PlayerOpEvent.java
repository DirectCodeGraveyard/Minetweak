package org.minetweak.event.player;

import org.minetweak.entity.Player;

public class PlayerOpEvent {

    private Player player;
    private String username;

    private boolean isOfflinePlayer;

    public PlayerOpEvent(Player playerInstance) {
        this.player = playerInstance;
        this.username = playerInstance.getName();
        isOfflinePlayer = false;
    }

    public PlayerOpEvent(String playerUsername) {
        this.username = playerUsername;
        isOfflinePlayer = true;
    }

    /**
     * Checks if the player is offline
     * @return if player is offline
     */
    public boolean isOfflinePlayer() {
        return isOfflinePlayer;
    }

    /**
     * Gets player instance
     * @return player instance
     */
    public Player getPlayer() {
        if (isOfflinePlayer) return null;
        return player;
    }

    /**
     * Gets player username
     * @return player username
     */
    public String getPlayerUsername() {
        return username;
    }

}
