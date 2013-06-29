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
