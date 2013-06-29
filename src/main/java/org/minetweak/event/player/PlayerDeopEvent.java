package org.minetweak.event.player;

import org.minetweak.entity.Player;

public class PlayerDeopEvent {

    private Player player;
    private String username;

    private boolean isOfflinePlayer;

    public PlayerDeopEvent(Player playerInstance) {
        this.player = playerInstance;
        this.username = playerInstance.getName();
        isOfflinePlayer = false;
    }

    public PlayerDeopEvent(String playerUsername) {
        this.username = playerUsername;
        isOfflinePlayer = true;
    }

    public boolean isOfflinePlayer() {
        return isOfflinePlayer;
    }

    public Player getPlayer() {
        if (isOfflinePlayer) return null;
        return player;
    }

    public String getPlayerUsername() {
        return username;
    }

}
