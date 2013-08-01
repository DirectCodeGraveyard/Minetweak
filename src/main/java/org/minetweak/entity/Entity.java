package org.minetweak.entity;

import net.minecraft.entity.EntityMob;
import net.minecraft.utils.AxisAlignedBB;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    protected net.minecraft.entity.Entity entity;

    /**
     * Constructor that allows you to generate an Entity using Minetweak's API
     *
     * @param entity MC Entity
     */
    public Entity(net.minecraft.entity.Entity entity) {
        this.entity = entity;
    }

    /**
     * Gets the MC Entity
     *
     * @return MC Entity
     */
    public net.minecraft.entity.Entity getEntity() {
        return entity;
    }

    /**
     * Checks if the Entity is invisible
     *
     * @return if entity is invisible
     */
    public boolean isInvisible() {
        return entity.isInvisible();
    }

    /**
     * Sets an Entity as invisible
     *
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
     *
     * @return if entity is dead
     */
    public boolean isDead() {
        return entity.isDead;
    }

    /**
     * Gets the nearby list of Mobs
     *
     * @param range range around mob
     * @return list of Mobs
     */
    public ArrayList<Mob> getNearbyMobs(int range) {
        ArrayList<Mob> mobs = new ArrayList<Mob>();
        double posX = entity.posX;
        double posY = entity.posY;
        double posZ = entity.posZ;
        List<net.minecraft.entity.Entity> entityList = entity.worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + range, posY + range, posZ + range));
        for (net.minecraft.entity.Entity entity : entityList) {
            if (entity instanceof EntityMob) {
                mobs.add(new Mob((EntityMob) entity));
            }
        }
        return mobs;
    }

    /**
     * Set the entity on fire for an amount of time, in seconds.
     * Cannot be used if the passed time was below the amount of fire seconds that already are in place.
     *
     * @param seconds Amount of time in seconds that you were to catch the entity on fire for.
     */
    public void setFire(int seconds) {
        entity.setFire(seconds);
    }

    /**
     * Extinguish the entity from fire.
     */
    public void extinguishFire() {
        entity.extinguish();
    }

    /**
     * Set the air level for the entity
     *
     * @param airLevel Entity air level
     */
    public void setAir(int airLevel) {
        entity.setAir(airLevel);
    }

    /**
     * Get the air level for the entity
     *
     * @return Entity air level
     */
    public int getAir() {
        return entity.getAir();
    }

    /**
     * Set the entity to sprint
     *
     * @param sprinting Sprinting toggle boolean; true if sprinting
     */
    public void setSprinting(boolean sprinting) {
        entity.setSprinting(sprinting);
    }

    /**
     * Check if the entity is sprinting
     *
     * @return True if sprinting
     */
    public boolean isSprinting() {
        return entity.isSprinting();
    }

    /**
     * Set the entity to sneak
     *
     * @param sneaking Sneaking toggle boolean; true if sneaking
     */
    public void setSneaking(boolean sneaking) {
        entity.setSneaking(sneaking);
    }

    /**
     * Check if the entity is sneaking
     *
     * @return True if sneaking
     */
    public boolean isSneaking() {
        return entity.isSneaking();
    }

    /**
     * Mount this entity onto another
     *
     * @param mountedEntity Entity to mount the class's upon
     */
    public void mountEntity(net.minecraft.entity.Entity mountedEntity) {
        entity.mountEntity(mountedEntity);
    }

    /**
     * Dismount this entity from another one
     */
    public void dismount() {
        entity.mountEntity(null);
    }

    /**
     * Sets the Entity as Preventable
     */
    public void noSpawn() {
        entity.preventEntitySpawning = true;
    }
}
