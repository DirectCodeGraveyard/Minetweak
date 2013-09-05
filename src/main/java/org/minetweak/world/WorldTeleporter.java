package org.minetweak.world;

import org.minetweak.entity.Player;

public class WorldTeleporter {
    private final World destination;

    /**
     * Constructs a World Teleporter
     *
     * @param destination Destination World
     */
    public WorldTeleporter(World destination) {
        this.destination = destination;
    }

    /**
     * Teleports the Player from the players current world
     * to the destination of this teleporter
     *
     * @param player player to teleport using this teleportation gate
     */
    public void teleportPlayer(Player player) {
        TeleportHelper.transferEntityToWorld(player.getPlayerMP(), player.getCurrentWorld().getWorldServer().getWorldInfo().getDimension(), player.getCurrentWorld().getWorldServer(), destination.getWorldServer());
    }
}
