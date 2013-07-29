package org.minetweak.world;

import org.minetweak.entity.Player;

public class WorldTeleporter {
    private World current;
    private World destination;

    /**
     * Constructs a World Teleporter
     *
     * @param current     Current World
     * @param destination Destination World
     */
    public WorldTeleporter(World current, World destination) {
        this.current = current;
        this.destination = destination;
    }

    /**
     * Teleports the Player from the world specified in the Constructor to the destination
     *
     * @param player player to teleport using this teleportation gate
     */
    public void teleportPlayer(Player player) {
        TeleportHelper.transferEntityToWorld(player.getPlayerMP(), current.getWorldServer().getWorldInfo().getDimension(), current.getWorldServer(), destination.getWorldServer());
    }
}
