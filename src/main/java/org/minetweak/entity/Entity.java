package org.minetweak.entity;

public class Entity {
    protected net.minecraft.src.Entity entity;

    public Entity(net.minecraft.src.Entity entity) {
        this.entity = entity;
    }

    public net.minecraft.src.Entity getEntity() {
        return entity;
    }

    public boolean isInvisible() {
        return entity.isInvisible();
    }

    public void setInvisible(boolean invisible) {
        entity.setInvisible(invisible);
    }

    public void setDead() {
        entity.setDead();
    }

    public boolean isDead() {
        return entity.isDead;
    }
}
