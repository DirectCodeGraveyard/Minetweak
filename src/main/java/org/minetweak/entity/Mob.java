package org.minetweak.entity;

import net.minecraft.src.EntityMob;

public class Mob extends Entity {

    public Mob(EntityMob mob) {
        super(mob);
    }

    public EntityMob getEntityMob() {
        return (EntityMob) entity;
    }

    public void setHealth(float health) {
        getEntityMob().setEntityHealth(health);
    }

    public float getHealth() {
        return getEntityMob().prevHealth;
    }

    public void heal(float health) {
        getEntityMob().heal(health);
    }

    public void setOnFire(Integer length) {
        getEntityMob().setFire(length);
    }

    public boolean isOnFire() {
        return getEntityMob().isBurning();
    }

    public boolean isImmuneToFire() {
        return getEntityMob().isImmuneToFire();
    }

    public void setPosition(double x, double y, double z) {
        getEntityMob().setPosition(x, y, z);
    }
}
