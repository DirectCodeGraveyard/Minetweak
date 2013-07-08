package org.minetweak.event.player;

import org.minetweak.Minetweak;
import org.minetweak.entity.Player;

public class PlayerOpEvent extends PlayerEvent {

    private Player player;
    private String username;

    private boolean isOfflinePlayer;

    public PlayerOpEvent(Player playerInstance) {
        super(playerInstance);
        this.username = playerInstance.getName();
        isOfflinePlayer = false;
    }

    public PlayerOpEvent(String playerUsername) {
        super(null);
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
     * Gets player username
     * @return player username
     */
    public String getPlayerUsername() {
        return username;
    }

}
