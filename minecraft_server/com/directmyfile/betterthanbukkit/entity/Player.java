package com.directmyfile.betterthanbukkit.entity;

public class Player {

    private String playerDisplayName;

    public Player(String playerDisplayName) {
        this.playerDisplayName = playerDisplayName;
    }

    public String getDisplayName() {
        return playerDisplayName;
    }

}
