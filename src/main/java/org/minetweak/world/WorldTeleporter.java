package org.minetweak.world;

import org.minetweak.Minetweak;
import org.minetweak.entity.Player;

public class WorldTeleporter {
    private World current;
    private World destination;

    public WorldTeleporter(World current, World destination) {
        this.current = current;
        this.destination = destination;
    }

    public void teleportPlayer(Player player) {
        Minetweak.info("Attempting to teleport " + player.getName());
        TeleportHelper.transferEntityToWorld(player.getPlayerMP(), current.getWorldServer().getWorldInfo().getDimension(), current.getWorldServer(), destination.getWorldServer());
    }
}
