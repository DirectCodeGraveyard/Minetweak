package org.minetweak.event.player;

import org.minetweak.entity.Player;

public class PlayerDeopEvent extends PlayerEvent {

    private Player player;
    private String username;

    private boolean isOfflinePlayer;

    public PlayerDeopEvent(Player playerInstance) {
        super(playerInstance);
        this.player = playerInstance;
        this.username = playerInstance.getName();
        isOfflinePlayer = false;
    }

    public PlayerDeopEvent(String playerUsername) {
        super(null);
        this.username = playerUsername;
        isOfflinePlayer = true;
    }

    /**
     * Check if player is offline
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

    @Override
    public Player getPlayer() {
        return player;
    }
}
