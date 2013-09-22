package org.minetweak.event.entity;

import org.minetweak.entity.Entity;
import org.minetweak.world.World;

public class EntitySpawnEvent implements EntityEvent {
    private Entity entity;

    public EntitySpawnEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public World getWorld() {
        return entity.getEntity().worldObj.getWorld();
    }
}
