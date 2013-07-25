package org.minetweak.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.ChunkCoordinates;

public class TeleportHelper {
    /**
     * Attempts to move an Entity to a World
     *
     * @param entity       Entity to Move
     * @param dimension    dimension to move to
     * @param oldServer  original server
     * @param targetServer new server
     */
    public static void transferEntityToWorld(Entity entity, int dimension, WorldServer oldServer, WorldServer targetServer) {
        double d0;
        double d1;

        if (entity.dimension == 1) {
            ChunkCoordinates chunkcoordinates;

            if (dimension == 1) {
                chunkcoordinates = targetServer.getSpawnPoint();
            } else {
                chunkcoordinates = targetServer.getEntrancePortalLocation();
            }

            d0 = (double) chunkcoordinates.posX;
            entity.posY = (double) chunkcoordinates.posY;
            d1 = (double) chunkcoordinates.posZ;
            entity.setLocationAndAngles(d0, entity.posY, d1, 90.0F, 0.0F);

            if (entity.isEntityAlive()) {
                oldServer.updateEntityWithOptionalForce(entity, false);
            }
        }
    }
}
