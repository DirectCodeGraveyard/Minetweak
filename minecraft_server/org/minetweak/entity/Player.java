package org.minetweak.entity;

import org.minetweak.Server;

public class Player {

    private String playerDisplayName;

    public Player(String playerDisplayName) {
        this.playerDisplayName = playerDisplayName;
    }

    public String getDisplayName() {
        return playerDisplayName;
    }

    public boolean kickPlayer() {
        return Server.kickPlayer(playerDisplayName);
    }

    public boolean banPlayer() {
        return Server.banPlayer(playerDisplayName);
    }

}
