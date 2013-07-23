package org.minetweak.entity;

import net.minecraft.entity.EntityMob;

public class Mob extends Entity {

    /**
     * Creates a Mob from EntityMob
     * @param mob EntityMob
     */
    public Mob(EntityMob mob) {
        super(mob);
    }

    /**
     * Gets the MC EntityMob
     * @return MC EntityMob
     */
    public EntityMob getEntityMob() {
        return (EntityMob) entity;
    }

    /**
     * Sets the health of the mob
     * @param health new health
     */
    public void setHealth(float health) {
        getEntityMob().setEntityHealth(health);
    }

    /**
     * Gets the Mob Health
     * @return health of mob
     */
    public float getHealth() {
        return getEntityMob().prevHealth;
    }

    /**
     * Heals a Mob
     * @param health health to add
     */
    public void heal(float health) {
        getEntityMob().heal(health);
    }

    /**
     * Sets a Mob on fire
     * @param length time for mob to be on fire
     */
    public void setOnFire(int length) {
        getEntityMob().setFire(length);
    }

    /**
     * Checks if a Mob is on FIre
     * @return if mob is on fire
     */
    public boolean isOnFire() {
        return getEntityMob().isBurning();
    }

    /**
     * Checks if Mob is immune to Fire
     * @return if mob is immune to fire
     */
    public boolean isImmuneToFire() {
        return getEntityMob().isImmuneToFire();
    }

    /**
     * Sets a Mob Position
     * @param x x-pos
     * @param y y-pos
     * @param z z-pos
     */
    public void setPosition(double x, double y, double z) {
        getEntityMob().setPosition(x, y, z);
    }
}
