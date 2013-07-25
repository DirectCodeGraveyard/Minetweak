package org.minetweak.server;

import net.minecraft.player.PlayerCapabilities;

public enum GameMode {
    NOT_SET(-1, ""),
    SURVIVAL(0, "survival"),
    CREATIVE(1, "creative"),
    ADVENTURE(2, "adventure");
    int id;
    String name;

    private GameMode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the ID of this game type
     */
    public int getID() {
        return this.id;
    }

    /**
     * Returns the name of this game type
     */
    public String getName() {
        return this.name;
    }

    /**
     * Configures the player capabilities based on the game type
     */
    public void configurePlayerCapabilities(PlayerCapabilities capabilities) {
        if (this == CREATIVE) {
            capabilities.allowFlying = true;
            capabilities.isCreativeMode = true;
            capabilities.disableDamage = true;
        } else {
            capabilities.allowFlying = false;
            capabilities.isCreativeMode = false;
            capabilities.disableDamage = false;
            capabilities.isFlying = false;
        }

        capabilities.allowEdit = !this.isAdventure();
    }

    /**
     * Returns true if this is the ADVENTURE game type
     */
    public boolean isAdventure() {
        return this == ADVENTURE;
    }

    /**
     * Returns true if this is the CREATIVE game type
     */
    public boolean isCreative() {
        return this == CREATIVE;
    }

    /**
     * Returns the game type with the specified ID, or SURVIVAL if none found. Args: id
     */
    public static GameMode getByID(int id) {
        GameMode[] modes = values();

        for (GameMode mode : modes) {
            if (mode.id == id) {
                return mode;
            }
        }

        return SURVIVAL;
    }
}
