package org.minetweak.entity;

import net.minecraft.entity.EntityTNTPrimed;

public class EntityTNT extends Entity {
    public EntityTNT(EntityTNTPrimed mcEntity) {
        super(mcEntity);
    }

    public void setFuse(int fuse) {
        getPrimedTNT().fuse = fuse;
    }

    public int getFuse() {
        return getPrimedTNT().fuse;
    }

    public void explode() {
        getPrimedTNT().explode();
    }

    public void setRadius(float radius) {
        getPrimedTNT().radius = radius;
    }

    public float getRadius() {
        return getPrimedTNT().radius;
    }

    public EntityTNTPrimed getPrimedTNT() {
        return (EntityTNTPrimed) getEntity();
    }
}
