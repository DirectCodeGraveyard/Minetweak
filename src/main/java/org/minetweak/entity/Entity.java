package org.minetweak.entity;

public class Entity {
    protected net.minecraft.src.Entity entity;

    /**
     * Creates a Minetweak Entity from MC's Entity
     * @param entity MC Entity
     */
    public Entity(net.minecraft.src.Entity entity) {
        this.entity = entity;
    }

    /**
     * Gets the MC Entity
     * @return MC Entity
     */
    public net.minecraft.src.Entity getEntity() {
        return entity;
    }

    /**
     * Checks if the Entity is invisible
     * @return if entity is invisible
     */
    public boolean isInvisible() {
        return entity.isInvisible();
    }

    /**
     * Sets an Entity as invisible
     * @param invisible if the entity should be invisible
     */
    public void setInvisible(boolean invisible) {
        entity.setInvisible(invisible);
    }

    /**
     * Kills Entity
     */
    public void setDead() {
        entity.setDead();
    }

    /**
     * Checks if entity is dead
     * @return if entity is dead
     */
    public boolean isDead() {
        return entity.isDead;
    }
}
