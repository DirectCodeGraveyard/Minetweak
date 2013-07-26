package org.minetweak.event.entity;

import net.minecraft.entity.EntityCreeper;

public class CreeperExplodeEvent implements EntityEvent {

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

    public boolean isCancelled() {
        return cancelled;
    }

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