package org.minetweak.entity.player;

import java.util.ArrayList;

/**
 * Used as a way to get the list of players who have been on the server before
 */
public class PlayerListFile {
    public ArrayList<String> players = new ArrayList<String>();

    public PlayerListFile(ArrayList<String> players) {
        this.players = players;
    }
}
