package org.minetweak.event.entity;

import net.minecraft.entity.EntityCreeper;
import org.minetweak.event.helper.Cancellable;

public class CreeperExplodeEvent implements EntityEvent, Cancellable {

    private EntityCreeper creeper;
    private boolean radiusChanged;
    private float explosionRadius;

    private boolean cancelled;

    public CreeperExplodeEvent(EntityCreeper creeper, float explosionRadius) {
        this.creeper = creeper;
        this.explosionRadius = explosionRadius;
    }

    public EntityCreeper getEntity() {
        return creeper;
    }

    public float getExplosionRadius() {
        return explosionRadius;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setExplosionRadius(float radius) {
        explosionRadius = radius;
        radiusChanged = true;
    }

    public boolean explosionRadiusChanged() {
        return radiusChanged;
    }
}
