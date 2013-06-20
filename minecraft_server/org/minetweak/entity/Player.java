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
        if (Server.kickPlayer(playerDisplayName)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean banPlayer() {
        if (Server.banPlayer(playerDisplayName)) {
            return true;
        } else {
            return false;
        }
    }

}
