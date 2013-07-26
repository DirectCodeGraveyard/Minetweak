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
     * @param worldserver  original server
     * @param worldserver1 new server
     */
    public static void transferEntityToWorld(Entity entity, int dimension, WorldServer worldserver, WorldServer worldserver1) {
        double moveFactor = 1;
        double d0;
        double d1;
        double d3 = entity.posX;
        double d4 = entity.posY;
        double d5 = entity.posZ;
        float f = entity.rotationYaw;

        if (entity.dimension == 1) {
            ChunkCoordinates chunkcoordinates;

            if (dimension == 1) {
                chunkcoordinates = worldserver1.getSpawnPoint();
            } else {
                chunkcoordinates = worldserver1.getEntrancePortalLocation();
            }

            d0 = (double) chunkcoordinates.posX;
            entity.posY = (double) chunkcoordinates.posY;
            d1 = (double) chunkcoordinates.posZ;
            entity.setLocationAndAngles(d0, entity.posY, d1, 90.0F, 0.0F);

            if (entity.isEntityAlive()) {
                worldserver.updateEntityWithOptionalForce(entity, false);
            }
        }
    }
}
