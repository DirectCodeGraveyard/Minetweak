package org.minetweak.entity;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityMob;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Gets the nearby list of Mobs
     * @param range range around mob
     * @return list of Mobs
     */
    public ArrayList<Mob> getNearbyMobs(int range) {
        ArrayList<Mob> mobs = new ArrayList<Mob>();
        double posX = entity.posX;
        double posY = entity.posY;
        double posZ = entity.posZ;
        List<net.minecraft.src.Entity> entityList = entity.worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + range, posY + range, posZ + range));
        for (net.minecraft.src.Entity entity : entityList) {
            if (entity instanceof EntityMob) {
                mobs.add(new Mob((EntityMob) entity));
            }
        }
        return mobs;
    }
}
