package org.minetweak.entity;

import net.minecraft.entity.EntityTNTPrimed;

/**
 * TNT - Explodes, and shit
 */
public class EntityTNT extends Entity {
    public EntityTNT(EntityTNTPrimed mcEntity) {
        super(mcEntity);
    }

    /**
     * Sets the Fuse Time
     *
     * @param fuse fuse time
     */
    public void setFuse(int fuse) {
        getPrimedTNT().fuse = fuse;
    }

    /**
     * Gets the Fuse Time
     *
     * @return fuse time
     */
    public int getFuse() {
        return getPrimedTNT().fuse;
    }

    /**
     * Makes the TNT Explode
     */
    public void explode() {
        getPrimedTNT().explode();
    }

    /**
     * Sets the Explosion Radius
     *
     * @param radius Explosion Radius
     */
    public void setRadius(float radius) {
        getPrimedTNT().radius = radius;
    }

    /**
     * Gets the Explosion Radius
     *
     * @return Explosion Radius
     */
    public float getRadius() {
        return getPrimedTNT().radius;
    }

    /**
     * Gets the MC Primed TNT Instance
     *
     * @return Primed TNT
     */
    public EntityTNTPrimed getPrimedTNT() {
        return (EntityTNTPrimed) getEntity();
    }
}
